package cn.xmo.domain.cargo;

import java.io.Serializable;
import java.util.Date;

public class Finance implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column co_finance.finance_id
     *
     * @mbg.generated
     */
    private String financeId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column co_finance.input_date
     *
     * @mbg.generated
     */
    private Date inputDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column co_finance.input_by
     *
     * @mbg.generated
     */
    private String inputBy;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column co_finance.state
     *
     * @mbg.generated
     */
    private Integer state;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column co_finance.create_by
     *
     * @mbg.generated
     */
    private String createBy;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column co_finance.create_dept
     *
     * @mbg.generated
     */
    private String createDept;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column co_finance.create_time
     *
     * @mbg.generated
     */
    private Date createTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column co_finance.finance_id
     *
     * @return the value of co_finance.finance_id
     *
     * @mbg.generated
     */
    public String getFinanceId() {
        return financeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column co_finance.finance_id
     *
     * @param financeId the value for co_finance.finance_id
     *
     * @mbg.generated
     */
    public void setFinanceId(String financeId) {
        this.financeId = financeId == null ? null : financeId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column co_finance.input_date
     *
     * @return the value of co_finance.input_date
     *
     * @mbg.generated
     */
    public Date getInputDate() {
        return inputDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column co_finance.input_date
     *
     * @param inputDate the value for co_finance.input_date
     *
     * @mbg.generated
     */
    public void setInputDate(Date inputDate) {
        this.inputDate = inputDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column co_finance.input_by
     *
     * @return the value of co_finance.input_by
     *
     * @mbg.generated
     */
    public String getInputBy() {
        return inputBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column co_finance.input_by
     *
     * @param inputBy the value for co_finance.input_by
     *
     * @mbg.generated
     */
    public void setInputBy(String inputBy) {
        this.inputBy = inputBy == null ? null : inputBy.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column co_finance.state
     *
     * @return the value of co_finance.state
     *
     * @mbg.generated
     */
    public Integer getState() {
        return state;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column co_finance.state
     *
     * @param state the value for co_finance.state
     *
     * @mbg.generated
     */
    public void setState(Integer state) {
        this.state = state;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column co_finance.create_by
     *
     * @return the value of co_finance.create_by
     *
     * @mbg.generated
     */
    public String getCreateBy() {
        return createBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column co_finance.create_by
     *
     * @param createBy the value for co_finance.create_by
     *
     * @mbg.generated
     */
    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column co_finance.create_dept
     *
     * @return the value of co_finance.create_dept
     *
     * @mbg.generated
     */
    public String getCreateDept() {
        return createDept;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column co_finance.create_dept
     *
     * @param createDept the value for co_finance.create_dept
     *
     * @mbg.generated
     */
    public void setCreateDept(String createDept) {
        this.createDept = createDept == null ? null : createDept.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column co_finance.create_time
     *
     * @return the value of co_finance.create_time
     *
     * @mbg.generated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column co_finance.create_time
     *
     * @param createTime the value for co_finance.create_time
     *
     * @mbg.generated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}