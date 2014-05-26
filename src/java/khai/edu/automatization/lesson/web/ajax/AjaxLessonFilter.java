/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package khai.edu.automatization.lesson.web.ajax;

/**
 *
 * @author Alex
 */
public class AjaxLessonFilter {
    private Integer group_id;
    private Integer disc_id;
    private Integer dt_id;
    private Integer prchair_id;
    private Integer rdchair_id;
    private Integer sem_id;
    private Integer ct_id;
    private Integer spec_id;
    private boolean zachet;
    private boolean exam;
    private boolean div_zachet;
    private boolean cp;
    private boolean cw;

    public Integer getGroup_id() {
        return group_id;
    }

    public void setGroup_id(Integer group_id) {
        this.group_id = group_id;
    }

    public Integer getDisc_id() {
        return disc_id;
    }

    public void setDisc_id(Integer disc_id) {
        this.disc_id = disc_id;
    }

    public Integer getDt_id() {
        return dt_id;
    }

    public void setDt_id(Integer dt_id) {
        this.dt_id = dt_id;
    }

    public Integer getPrchair_id() {
        return prchair_id;
    }

    public void setPrchair_id(Integer prchair_id) {
        this.prchair_id = prchair_id;
    }

    public Integer getRdchair_id() {
        return rdchair_id;
    }

    public void setRdchair_id(Integer rdchair_id) {
        this.rdchair_id = rdchair_id;
    }

    public Integer getSem_id() {
        return sem_id;
    }

    public void setSem_id(Integer sem_id) {
        this.sem_id = sem_id;
    }

    public Integer getCt_id() {
        return ct_id;
    }

    public void setCt_id(Integer ct_id) {
        this.ct_id = ct_id;
    }

    public Integer getSpec_id() {
        return spec_id;
    }

    public void setSpec_id(Integer spec_id) {
        this.spec_id = spec_id;
    }

    public boolean isZachet() {
        return zachet;
    }

    public void setZachet(boolean zachet) {
        this.zachet = zachet;
    }

    public boolean isExam() {
        return exam;
    }

    public void setExam(boolean exam) {
        this.exam = exam;
    }

    public boolean isDiv_zachet() {
        return div_zachet;
    }

    public void setDiv_zachet(boolean div_zachet) {
        this.div_zachet = div_zachet;
    }

    public boolean isCp() {
        return cp;
    }

    public void setCp(boolean cp) {
        this.cp = cp;
    }

    public boolean isCw() {
        return cw;
    }

    public void setCw(boolean cw) {
        this.cw = cw;
    }
}
