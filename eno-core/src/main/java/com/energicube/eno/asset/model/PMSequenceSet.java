package com.energicube.eno.asset.model;

/**
 * PM的作业计划序列
 *
 * @author CHENPING
 */
public class PMSequenceSet implements java.io.Serializable {

    private static final long serialVersionUID = 1573320917428784920L;
    private JobPlan jobPlan;
    private PMSequence pmSequence;

    public JobPlan getJobPlan() {
        return jobPlan;
    }

    public void setJobPlan(JobPlan jobPlan) {
        this.jobPlan = jobPlan;
    }

    public PMSequence getPmSequence() {
        return pmSequence;
    }

    public void setPmSequence(PMSequence pmSequence) {
        this.pmSequence = pmSequence;
    }

}
