/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package khai.edu.automatization.lesson.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import khai.edu.automatization.lesson.dao.ChairDao;
import khai.edu.automatization.lesson.dao.ControlTypeDao;
import khai.edu.automatization.lesson.dao.DiscTypeDao;
import khai.edu.automatization.lesson.dao.DisciplineDao;
import khai.edu.automatization.lesson.dao.GroupDao;
import khai.edu.automatization.lesson.dao.LazyInitializer;
import khai.edu.automatization.lesson.dao.LessonPlanDao;
import khai.edu.automatization.lesson.dao.SemesterDao;
import khai.edu.automatization.lesson.dao.SpecialityDao;
import khai.edu.automatization.lesson.dao.TeacherDao;
import khai.edu.automatization.lesson.model.Chair;
import khai.edu.automatization.lesson.model.ControlType;
import khai.edu.automatization.lesson.model.DiscType;
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
    
    public static final String FILE_NAME = "D:\\STUDYING\\JAVA\\AUTOMATIZATION\\0032--all--2013.01.07.xls";
    
    public static final int START_ROW = 1;
    
    public static final int END_ROW = 100;
    
    private TeacherDao teacherDao;
    private LazyInitializer lazyInit;
    private LessonPlanDao lessonDao;
    private GroupDao groupDao;
    private ChairDao chairDao;
    private DiscTypeDao discTypeDao;
    private DisciplineDao disciplineDao;
    private SpecialityDao specialityDao;
    private SemesterDao semesterDao;
    private ControlTypeDao controlDao;
    
    private TransactionTemplate txTemplate;
    
    private FileInputStream inputFile;
    private HSSFWorkbook wbook;
    private HSSFSheet wsheet;
    
    
    public static void main(String[] args){
        ApplicationContext appContext = null;
        try{
            appContext = new ClassPathXmlApplicationContext("khai/edu/automatization/lesson/test/appContext.xml");
        } catch(BeansException bex){
            System.out.println("Ошибка при создании контекста Spring\n" + bex.getMessage());
            return;
        }
        PlansImporter importer = null; // = new PlansImporter();
        try {
            importer = new PlansImporter(PlansImporter.FILE_NAME);
        } catch (Exception ex){
            System.out.println("Error occured when creating importer\n" + ex.getMessage());
            return;
        }
        importer.setChairDao((ChairDao)appContext.getBean("chairDao"));
        importer.setDiscTypeDao((DiscTypeDao)appContext.getBean("discTypeDao"));
        importer.setDisciplineDao((DisciplineDao)appContext.getBean("disciplineDao"));
        importer.setGroupDao((GroupDao)appContext.getBean("groupDao"));
        importer.setLazyInit((LazyInitializer)appContext.getBean("lazyInitializer"));
        importer.setLessonDao((LessonPlanDao)appContext.getBean("lessonPlanDao"));
        importer.setSemesterDao((SemesterDao)appContext.getBean("semesterDao"));
        importer.setSpecialityDao((SpecialityDao)appContext.getBean("specialityDao"));
        importer.setTeacherDao((TeacherDao)appContext.getBean("teacherDao"));
        importer.setControlDao((ControlTypeDao)appContext.getBean("controlDao"));
        HibernateTransactionManager txManager = (HibernateTransactionManager)appContext.getBean("transactionManager");
        importer.setTxTemplate(new TransactionTemplate(txManager));
        
        System.out.println("Начинаем парсить данные");
        System.out.println("Читаем записи " + PlansImporter.START_ROW + " - " + PlansImporter.END_ROW + " = " + (PlansImporter.END_ROW - PlansImporter.START_ROW));
        int saved_count = importer.saveRows(START_ROW, END_ROW);
        System.out.println("Сохранено " + saved_count);
    }
    
    public PlansImporter(String fname) throws FileNotFoundException, IOException {
        this.inputFile = new FileInputStream(fname);
        wbook = new HSSFWorkbook(this.inputFile);
        wsheet = wbook.getSheet("Лист1");
    }
    
    public int saveRows(int position, int count){
        //открыли книгу и открыли "Лист1" в конструкторе
        int rows_count = this.wsheet.getLastRowNum() + 1;
        int len = position + count;
        int saved_rows = 0;
        //пройдемся по строкам
        for (int i=position; i<len && i<rows_count; i++){
            HSSFRow wrow = wsheet.getRow(i);
            try {
                this.transactSaveRow(wrow);
                saved_rows++;
            } catch (RuntimeException rex) {
                System.err.println(rex.getMessage());
            }  
        }
        return saved_rows;
    }
    
    public void transactSaveRow(final HSSFRow row){
        txTemplate.execute( new TransactionCallback<Void>() {
                    public Void doInTransaction(TransactionStatus txStatus){
                        try {
                            saveData(row);
                        } catch(RuntimeException rex){
                            txStatus.setRollbackOnly();
                            throw rex;
                        }
                        return null;
                    }
            }
        );
    }
    
    private void saveData(HSSFRow row){
        if (row == null) return;
        String semester = row.getCell(Cells.SEMESTER.i()).toString();
        String prod_chair = row.getCell(Cells.PRODUCE_CHAIR.i()).toString();
        String speciality = row.getCell(Cells.SPECIALITY.i()).toString();   
        String group = row.getCell(Cells.GROUP.i()).toString();
        String discipline = row.getCell(Cells.DISCIPLINE.i()).toString();
        String read_chair = row.getCell(Cells.READ_CHAIR.i()).toString();

        int lection_hours = (int)Double.parseDouble(row.getCell(Cells.LECTION_HOURS.i()).toString());
        int practice_hours = (int)Double.parseDouble(row.getCell(Cells.PRACTICE_HOURS.i()).toString());
        int lab_hours = (int)Double.parseDouble(row.getCell(Cells.LAB_HOURS.i()).toString());
        
        boolean zachet = (int)Double.parseDouble(row.getCell(Cells.ZACHET.i()).toString()) > 0;
        boolean div_zachet = (int)Double.parseDouble(row.getCell(Cells.DIV_ZACHET.i()).toString()) > 0;
        boolean exam = (int)Double.parseDouble(row.getCell(Cells.EXAM.i()).toString()) > 0;

        //сначала посохранять все обьекты, связав их между собой
        //проверить существует ли такой план уже в базе (поиск по фильтру)
        //- да то просто добавить к нему группу
        //- иначе создать новый план и сохранить
        // если не нужно откатить сохрание бросить RuntimeException

        String str_control = null;
        if (zachet){
            str_control = "залік";
        } else if (div_zachet) {
            str_control = "диф.залік";
        } else if (exam) {
            str_control = "екзамен";
        }
        ControlType ctype = null;
        if (str_control != null) {
            ctype = controlDao.getByName(str_control);
            if (ctype == null) {
                ctype = new ControlType();
                ctype.setName(str_control);
                controlDao.saveOrUpdate(ctype);
            }
        }
        
        Semester sem = semesterDao.getByName(semester);
        if (sem == null){
            sem = new Semester();
            sem.setName(semester);
            sem.fillBySemester(semester);
            semesterDao.saveOrUpdate(sem);
        }

        Chair pr_ch = chairDao.getByName(prod_chair);
        if (pr_ch == null){
            pr_ch = new Chair();
            pr_ch.setName(prod_chair);
            chairDao.saveOrUpdate(pr_ch);
        }
 
        Chair rd_ch = chairDao.getByName(read_chair);
        if (rd_ch == null){
            rd_ch = new Chair();
            rd_ch.setName(read_chair);
            chairDao.saveOrUpdate(rd_ch);
        }

        //специальность есть у группы, а сама специальность на выпускающей кафедре
        Speciality spec = specialityDao.getByName(speciality);
        if (spec == null){
            spec = new Speciality();
            spec.setName(speciality);
            spec.setProduceChair(pr_ch);
            specialityDao.saveOrUpdate(spec);
        }

        Group gr = groupDao.getByName(group);
        if (gr == null){
            gr = new Group();
            gr.setName(group);
            gr.setSpeciality(spec);
            groupDao.saveOrUpdate(gr);
        }
        
        List<Discipline> disc_list = disciplineDao.getByName(discipline, rd_ch);
        Discipline disc = disc_list != null ? disc_list.get(0) : null;
        if (disc == null){
            disc = new Discipline();
            disc.setName(discipline);
            disc.setReadingChair(rd_ch);
            disciplineDao.saveOrUpdate(disc);
        }
        
        LessonPlan lp = null;

        //проверяем типы плана
        if (lection_hours > 0){
            //если план лекция то к нему прикрепляется сразу несколько групп одной специальности
            DiscType dt = discTypeDao.getByName("лекція");
            if (dt == null){
                dt = new DiscType();
                dt.setName("лекція");
                discTypeDao.saveOrUpdate(dt);
            }
            //сперва посмотрим есть ли план с такой группой
            List<LessonPlan> list = lessonDao.getLessonByFilter(rd_ch, pr_ch, spec, gr, disc, dt, sem, ctype);
            if (list != null){
                //план существует
                //throw new RuntimeException("Existing row");
                System.err.println("Existing row");
            } else {
                //плана с нашей группой нет, проверим есть ли вообще такой план
                List<LessonPlan> list_nongroup = lessonDao.getLessonByFilter(rd_ch, pr_ch, spec, null, disc, dt, sem, ctype);
                if (list_nongroup != null){
                    //есть план, просто прикрепим к нему группу
                    lp = list_nongroup.get(0);
                    lp.getGroups().add(gr);
                } else {
                    //такого плана совсем нет, создаем новый план
                    lp = new LessonPlan();
                    lp.setControlType(ctype);
                    lp.setDiscType(dt);
                    lp.setDiscipline(disc);
                    lp.getGroups().add(gr);
                    lp.setHours(lection_hours);
                    lp.setProducedChair(pr_ch);
                    lp.setReadingChair(rd_ch);
                    lp.setSemester(sem);
                    lp.setSpeciality(spec);   
                }
                lessonDao.saveOrUpdate(lp); 
            } 
        }
        //еще практика и лабы
        if (lab_hours > 0){
            DiscType dt = discTypeDao.getByName("л.р.");
            if (dt == null){
                dt = new DiscType();
                dt.setName("л.р.");
                discTypeDao.saveOrUpdate(dt);
            }
            List<LessonPlan> list = lessonDao.getLessonByFilter(rd_ch, pr_ch, spec, gr, disc, dt, sem, ctype);
            if (list != null){
                //план уже есть, сохранять не нужно
                //throw new RuntimeException("Existing row");
                System.err.println("Existing row");
            } else {
                //плана нет, создаем
                lp = new LessonPlan();
                lp.setControlType(ctype);
                lp.setDiscType(dt);
                lp.setDiscipline(disc);
                lp.getGroups().add(gr);
                lp.setHours(lab_hours);
                lp.setProducedChair(pr_ch);
                lp.setReadingChair(rd_ch);
                lp.setSemester(sem);
                lp.setSpeciality(spec);  
                lessonDao.saveOrUpdate(lp);
            }
        }
        if (practice_hours > 0){
            DiscType dt = discTypeDao.getByName("практика");
            if (dt == null){
                dt = new DiscType();
                dt.setName("практика");
                discTypeDao.saveOrUpdate(dt);
            }
            List<LessonPlan> list = lessonDao.getLessonByFilter(rd_ch, pr_ch, spec, gr, disc, dt, sem, ctype);
            if (list != null){
                //план уже есть, сохранять не нужно
                //throw new RuntimeException("Existing row");
                System.err.println("Existing row");
            } else {
                //плана нет, создаем
                lp = new LessonPlan();
                lp.setControlType(ctype);
                lp.setDiscType(dt);
                lp.setDiscipline(disc);
                lp.getGroups().add(gr);
                lp.setHours(practice_hours);
                lp.setProducedChair(pr_ch);
                lp.setReadingChair(rd_ch);
                lp.setSemester(sem);
                lp.setSpeciality(spec);  
                lessonDao.saveOrUpdate(lp);
            }
        }
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize(); //To change body of generated methods, choose Tools | Templates.
        if (this.inputFile != null){
            try{
                this.inputFile.close();
            } catch(IOException ex) {}
        }
    }

    public TeacherDao getTeacherDao() {
        return teacherDao;
    }

    public void setTeacherDao(TeacherDao teacherDao) {
        this.teacherDao = teacherDao;
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

    public DiscTypeDao getDiscTypeDao() {
        return discTypeDao;
    }

    public void setDiscTypeDao(DiscTypeDao discTypeDao) {
        this.discTypeDao = discTypeDao;
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

    public ControlTypeDao getControlDao() {
        return controlDao;
    }

    public void setControlDao(ControlTypeDao controlDao) {
        this.controlDao = controlDao;
    }

}
