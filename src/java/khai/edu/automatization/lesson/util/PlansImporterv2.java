package khai.edu.automatization.lesson.util;

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
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

/**
 *
 * @author Alex
 */
public class PlansImporterv2 {
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
    
    
    public void transactSaveRow(final HSSFRow row){
        txTemplate.execute( new TransactionCallback<Void>() {
                    public Void doInTransaction(TransactionStatus txStatus){
                        try {
                            saveRow(row);
                        } catch(RuntimeException rex){
                            txStatus.setRollbackOnly();
                            throw rex;
                        }
                        return null;
                    }
            }
        );
    }
    
    private void saveRow(HSSFRow row) {
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
        int lections_1half_hours = (int)Double.parseDouble(row.getCell(Cells.HOURS_LECT_1HALF.i()).toString());
        int lections_2half_hours = (int)Double.parseDouble(row.getCell(Cells.HOURS_LECT_2HALF.i()).toString());
        int lab_1half_hours = (int)Double.parseDouble(row.getCell(Cells.HOURS_LAB_1HALF.i()).toString());
        int lab_2half_hours = (int)Double.parseDouble(row.getCell(Cells.HOURS_LAB_2HALF.i()).toString());
        int pr_1half_hours = (int)Double.parseDouble(row.getCell(Cells.HOURS_PR_1HALF.i()).toString());
        int pr_2half_hours = (int)Double.parseDouble(row.getCell(Cells.HOURS_PR_2HALF.i()).toString());
        int hours_1half = (int)Double.parseDouble(row.getCell(Cells.HOURS_SEMESTER_1HALF.i()).toString());
        int hours_2half = (int)Double.parseDouble(row.getCell(Cells.HOURS_SEMESTER_2HALF.i()).toString());
        int hours_total = (int)Double.parseDouble(row.getCell(Cells.HOURS_ALL.i()).toString());
        int hours_own = (int)Double.parseDouble(row.getCell(Cells.HOURS_OWN.i()).toString());
        
        boolean zachet = (int)Double.parseDouble(row.getCell(Cells.ZACHET.i()).toString()) > 0;
        boolean div_zachet = (int)Double.parseDouble(row.getCell(Cells.DIV_ZACHET.i()).toString()) > 0;
        boolean exam = (int)Double.parseDouble(row.getCell(Cells.EXAM.i()).toString()) > 0;
        boolean rgr = (int)Double.parseDouble(row.getCell(Cells.RGR.i()).toString()) > 0;
        boolean rr = (int)Double.parseDouble(row.getCell(Cells.RR.i()).toString()) > 0;
        boolean rk = (int)Double.parseDouble(row.getCell(Cells.RK.i()).toString()) > 0;

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
}
