package khai.edu.automatization.lesson.util;

/**
 *
 * @author Alex
 */
public enum Cells {
    SEMESTER(0),
    FACULTY(1),
    PRODUCE_CHAIR(2),
    SPECIALITY(3),
    GROUP(4),
    DISCIPLINE(5),
    READ_CHAIR(6),
    RGR(7),
    RR(8),
    RK(9),
    COURSE_WORK(10),
    COURSE_PROJECT(11),
    ZACHET(12),
    DIV_ZACHET(13),
    EXAM(14),
    HOURS_LECT_1HALF(15),
    HOURS_LECT_2HALF(16),
    HOURS_LAB_1HALF(17),
    HOURS_LAB_2HALF(18),
    HOURS_PR_1HALF(19),
    HOURS_PR_2HALF(20),
    LECTION_HOURS(21),
    LAB_HOURS(22),
    PRACTICE_HOURS(23),
    HOURS_SEMESTER_1HALF(24),
    HOURS_SEMESTER_2HALF(25),
    HOURS_ALL(26),
    HOURS_OWN(27),
    HOURS_LECT_WEEK_1HALF(28),
    HOURS_LECT_WEEK_2HALF(29),
    HOURS_LAB_WEEK_1HALF(30),
    HOURS_LAB_WEEK_2HALF(31),
    HOURS_PR_WEEK_1HALF(32),
    HOURS_PR_WEEK_2HALF(33);
    
    private int index;

    Cells(int i) {
        this.index = i;
    }

    public int i() {
        return this.index;
    }
}
