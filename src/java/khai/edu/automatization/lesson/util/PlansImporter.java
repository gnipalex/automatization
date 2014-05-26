package khai.edu.automatization.lesson.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import khai.edu.automatization.lesson.dao.ChairDao;
import khai.edu.automatization.lesson.dao.DisciplineDao;
import khai.edu.automatization.lesson.dao.GroupDao;
import khai.edu.automatization.lesson.dao.LazyInitializer;
import khai.edu.automatization.lesson.dao.LessonPlanDao;
import khai.edu.automatization.lesson.dao.SemesterDao;
import khai.edu.automatization.lesson.dao.SpecialityDao;
import khai.edu.automatization.lesson.model.Chair;
import khai.edu.automatization.lesson.model.Discipline;
import khai.edu.automatization.lesson.model.Group;
import khai.edu.automatization.lesson.model.LessonPlan;
import khai.edu.automatization.lesson.model.Semester;
import khai.edu.automatization.lesson.model.Speciality;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

/**
 *
 * @author Alex
 */
public class PlansImporter {

    private LazyInitializer lazyInit;
    private LessonPlanDao lessonDao;
    private GroupDao groupDao;
    private ChairDao chairDao;
    private DisciplineDao disciplineDao;
    private SpecialityDao specialityDao;
    private SemesterDao semesterDao;
    private TransactionTemplate txTemplate;
    
    private File file;
    private InputStream input;
    
    private boolean overwrite;
    private String producedChairName = null;

    public PlansImporter(File file) {
        this.file = file;
    }

    public PlansImporter(File file, boolean overwrite){
        this(file);
        this.overwrite = overwrite;
    }
    
    public PlansImporter(InputStream input){
        this.input = input;
    }
    
    public PlansImporter(InputStream input, boolean overwrite){
        this(input);
        this.overwrite = overwrite;
    }
    
    public PlansImporter(InputStream input, boolean overwrite, String producedChairName) {
        this(input, overwrite);
        this.producedChairName = producedChairName;
    }

    public PlansImporter(File file, boolean overwrite, String producedChairName) {
        this(file, overwrite);
        if (producedChairName == null){
            throw new NullPointerException("producedChairName can't be null");
        }
        this.producedChairName = producedChairName;
    }

    /**
     * Parse rows of XLS file and save to DB
     * @param start - start position
     * @param count - count of rows to be saved
     * @return saved rows count
     * Specify start = 0 and count = 0 to read whole table
     * @throws FileNotFoundException
     * @throws IOException
     * @throws khai.edu.automatization.lesson.util.PlansImporter.PlansImporterException 
     */
    public int saveRowsToDB(int start, int count) throws FileNotFoundException, IOException, PlansImporterException {
        if (start < 0 || count < 0) {
            throw new PlansImporterException("incorrect params " + start + " " + count);
        }
        InputStream inputStream = null;
        try {
            if (this.input != null){
                inputStream = this.input;
            } else if (this.file != null) {
                inputStream = new FileInputStream(this.file);
            } else {
                throw new PlansImporterException("no input specified");
            }
            HSSFWorkbook wbook = new HSSFWorkbook(inputStream);
            HSSFSheet wsheet = wbook.getSheetAt(0);
            if (wsheet == null) {
                throw new PlansImporterException("sheet not found");
            }
            if (wsheet.getLastRowNum() + 1 < start) {
                throw new PlansImporterException("start index more than actual rows count");
            }
            int saved_rows_count = 0;
            int start_row = 0;
            int end_row = 0;
            if (start == 0 && count == 0){
                //читать всю таблицу
                start_row = 1;
                end_row = wsheet.getLastRowNum() + 1;
            } else {
                start_row = start;
                end_row = start + count;
            }
            for (int i = start_row; i< end_row && i < wsheet.getLastRowNum() + 1; i++) {
                HSSFRow wrow = wsheet.getRow(i);
                //проверка по имени назначающей кафедры
                String prChair = wrow.getCell(Cells.PRODUCE_CHAIR.i()).toString();
                if (this.producedChairName != null && !prChair.equalsIgnoreCase(this.producedChairName)){
                    continue;
                }
                try {
                    this.transactSaveRow(wrow, this.overwrite);
                    saved_rows_count++;
                } catch (Exception ex) {
                    System.err.println(ex.getMessage());
                }
            }
            return saved_rows_count;
        } finally {
            if (inputStream != null) inputStream.close();
        }
    }
    
    

    private static class PlansImporterException extends Exception {
        public PlansImporterException(String message) {
            super(message);
        }
    }

    public void transactSaveRow(final HSSFRow row, final boolean overwrite) {
        txTemplate.execute(new TransactionCallback<Void>() {
            public Void doInTransaction(TransactionStatus txStatus) {
                try {
                    saveRow(row, overwrite);
                } catch (RuntimeException rex) {
                    txStatus.setRollbackOnly();
                    throw rex;
                }
                return null;
            }
        });
    }

    private void saveRow(HSSFRow row, boolean overwrite) {
        if (row == null) {
            return;
        }
        String semester = row.getCell(Cells.SEMESTER.i()).toString();
        String prod_chair = row.getCell(Cells.PRODUCE_CHAIR.i()).toString();
        String speciality = row.getCell(Cells.SPECIALITY.i()).toString();
        String group = row.getCell(Cells.GROUP.i()).toString();
        String discipline = row.getCell(Cells.DISCIPLINE.i()).toString();
        String read_chair = row.getCell(Cells.READ_CHAIR.i()).toString();

        Semester sem = semesterDao.getByName(semester);
        if (sem == null) {
            sem = new Semester();
            sem.setName(semester);
            sem.fillBySemester(semester);
            semesterDao.saveOrUpdate(sem);
        }

        Chair prod_ch = chairDao.getByName(prod_chair);
        if (prod_ch == null) {
            prod_ch = new Chair();
            prod_ch.setName(prod_chair);
            chairDao.saveOrUpdate(prod_ch);
        }

        Chair read_ch = chairDao.getByName(read_chair);
        if (read_ch == null) {
            read_ch = new Chair();
            read_ch.setName(read_chair);
            chairDao.saveOrUpdate(read_ch);
        }

        //специальность есть у группы, а сама специальность на выпускающей кафедре
        Speciality spec = specialityDao.getByName(speciality);
        if (spec == null) {
            spec = new Speciality();
            spec.setName(speciality);
            spec.setProduceChair(prod_ch);
            specialityDao.saveOrUpdate(spec);
        }

        Group gr = groupDao.getByName(group);
        if (gr == null) {
            gr = new Group();
            gr.setName(group);
            gr.setSpeciality(spec);
            groupDao.saveOrUpdate(gr);
        }

        List<Discipline> disc_list = disciplineDao.getByName(discipline, read_ch);
        Discipline disc = disc_list != null ? disc_list.get(0) : null;
        if (disc == null) {
            disc = new Discipline();
            disc.setName(discipline);
            disc.setReadingChair(read_ch);
            disciplineDao.saveOrUpdate(disc);
        }

        //проверяем существует ли запись о плане в базе
        List<LessonPlan> list = lessonDao.getLessonByFilter(read_ch, prod_ch, spec, gr, disc, sem);
        LessonPlan lp = null;
        if (list != null && list.size() > 0) {
            if (overwrite) {
                lp = list.get(0);
            } else {
                throw new RuntimeException("existing plan in database");
            }
        } else {
            lp = new LessonPlan();
        }

        int lection_hours = (int) Double.parseDouble(row.getCell(Cells.LECTION_HOURS.i()).toString());
        int practice_hours = (int) Double.parseDouble(row.getCell(Cells.PRACTICE_HOURS.i()).toString());
        int lab_hours = (int) Double.parseDouble(row.getCell(Cells.LAB_HOURS.i()).toString());
        int lections_1half_hours = (int) Double.parseDouble(row.getCell(Cells.HOURS_LECT_1HALF.i()).toString());
        int lections_2half_hours = (int) Double.parseDouble(row.getCell(Cells.HOURS_LECT_2HALF.i()).toString());
        int lab_1half_hours = (int) Double.parseDouble(row.getCell(Cells.HOURS_LAB_1HALF.i()).toString());
        int lab_2half_hours = (int) Double.parseDouble(row.getCell(Cells.HOURS_LAB_2HALF.i()).toString());
        int pr_1half_hours = (int) Double.parseDouble(row.getCell(Cells.HOURS_PR_1HALF.i()).toString());
        int pr_2half_hours = (int) Double.parseDouble(row.getCell(Cells.HOURS_PR_2HALF.i()).toString());
        int hours_1half = (int) Double.parseDouble(row.getCell(Cells.HOURS_SEMESTER_1HALF.i()).toString());
        int hours_2half = (int) Double.parseDouble(row.getCell(Cells.HOURS_SEMESTER_2HALF.i()).toString());
        int hours_total = (int) Double.parseDouble(row.getCell(Cells.HOURS_ALL.i()).toString());
        int hours_own = (int) Double.parseDouble(row.getCell(Cells.HOURS_OWN.i()).toString());

        boolean zachet = (int) Double.parseDouble(row.getCell(Cells.ZACHET.i()).toString()) > 0;
        boolean div_zachet = (int) Double.parseDouble(row.getCell(Cells.DIV_ZACHET.i()).toString()) > 0;
        boolean exam = (int) Double.parseDouble(row.getCell(Cells.EXAM.i()).toString()) > 0;
        boolean rgr = (int) Double.parseDouble(row.getCell(Cells.RGR.i()).toString()) > 0;
        boolean rr = (int) Double.parseDouble(row.getCell(Cells.RR.i()).toString()) > 0;
        boolean rk = (int) Double.parseDouble(row.getCell(Cells.RK.i()).toString()) > 0;
        boolean cp = (int) Double.parseDouble(row.getCell(Cells.COURSE_PROJECT.i()).toString()) > 0;
        boolean cw = (int) Double.parseDouble(row.getCell(Cells.COURSE_WORK.i()).toString()) > 0;

        lp.setGroup(gr);
        lp.setProducedChair(prod_ch);
        lp.setReadingChair(read_ch);
        lp.setSemester(sem);
        lp.setSpeciality(spec);
        lp.setDiscipline(disc);

        lp.setCp(cp);
        lp.setCw(cw);
        lp.setDiscipline(disc);
        lp.setDiv_zachet(div_zachet);
        lp.setExam(exam);
        lp.setZachet(zachet);
        lp.setRgr(rgr);
        lp.setRr(rr);
        lp.setRk(rk);

        lp.setHours1Half(hours_1half);
        lp.setHours2Half(hours_2half);
        lp.setHoursAll(hours_total);
        lp.setHoursOwn(hours_own);

        lp.setHoursLect1Half(lections_1half_hours);
        lp.setHoursLect2Half(lections_2half_hours);
        lp.setHoursLectAll(lection_hours);
        lp.setHoursLab1Half(lab_1half_hours);
        lp.setHoursLab2Half(lab_2half_hours);
        lp.setHoursLabAll(lab_hours);
        lp.setHoursPract1Half(pr_1half_hours);
        lp.setHoursPract2Half(pr_2half_hours);
        lp.setHoursPractAll(practice_hours);

        lessonDao.saveOrUpdate(lp);
    }

    public LazyInitializer getLazyInit() {
        return lazyInit;
    }

    public void setLazyInit(LazyInitializer lazyInit) {
        this.lazyInit = lazyInit;
    }

    public LessonPlanDao getLessonDao() {
        return lessonDao;
    }

    public void setLessonDao(LessonPlanDao lessonDao) {
        this.lessonDao = lessonDao;
    }

    public GroupDao getGroupDao() {
        return groupDao;
    }

    public void setGroupDao(GroupDao groupDao) {
        this.groupDao = groupDao;
    }

    public ChairDao getChairDao() {
        return chairDao;
    }

    public void setChairDao(ChairDao chairDao) {
        this.chairDao = chairDao;
    }

    public DisciplineDao getDisciplineDao() {
        return disciplineDao;
    }

    public void setDisciplineDao(DisciplineDao disciplineDao) {
        this.disciplineDao = disciplineDao;
    }

    public SpecialityDao getSpecialityDao() {
        return specialityDao;
    }

    public void setSpecialityDao(SpecialityDao specialityDao) {
        this.specialityDao = specialityDao;
    }

    public SemesterDao getSemesterDao() {
        return semesterDao;
    }

    public void setSemesterDao(SemesterDao semesterDao) {
        this.semesterDao = semesterDao;
    }

    public TransactionTemplate getTxTemplate() {
        return txTemplate;
    }

    public void setTxTemplate(TransactionTemplate txTemplate) {
        this.txTemplate = txTemplate;
    }
    
    public static void main(String[] a){
        ApplicationContext appContext = null;
        try{
            appContext = new ClassPathXmlApplicationContext("khai/edu/automatization/lesson/util/appContext_forImporter.xml");
        } catch(BeansException bex){
            System.out.println("Ошибка при создании контекста Spring\n" + bex.getMessage());
            return;
        }
        File f = new File("D:\\STUDYING\\JAVA\\AUTOMATIZATION\\0032--all--2013.01.07.xls");
        PlansImporter importer = new PlansImporter(f, false);
        importer.setChairDao((ChairDao)appContext.getBean("chairDao"));
        importer.setDisciplineDao((DisciplineDao)appContext.getBean("disciplineDao"));
        importer.setGroupDao((GroupDao)appContext.getBean("groupDao"));
        importer.setLazyInit((LazyInitializer)appContext.getBean("lazyInitializer"));
        importer.setLessonDao((LessonPlanDao)appContext.getBean("lessonPlanDao"));
        importer.setSemesterDao((SemesterDao)appContext.getBean("semesterDao"));
        importer.setSpecialityDao((SpecialityDao)appContext.getBean("specialityDao"));
        HibernateTransactionManager txManager = (HibernateTransactionManager)appContext.getBean("transactionManager");
        importer.setTxTemplate(new TransactionTemplate(txManager));
        
        System.out.println("Начинаем парсить данные");
        System.out.println("Читаем записи с 1 100 штук");
        int saved_count = 0;
        try{
            saved_count = importer.saveRowsToDB(1, 100);
        } catch (Exception ex) {
            System.out.println(ex);
        }
        System.out.println("Сохранено " + saved_count);
    }
    
    
}
