package com.overops.plugins.model;

import com.takipi.api.client.util.cicd.OOReportEvent;
import com.takipi.api.client.util.cicd.QualityGateReport;
import com.takipi.api.client.util.regression.RegressionInput;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class QualityReport {

    private final List<OOReportEvent> newIssues;
    private final List<OOReportRegressedEvent> regressions;
    private final List<OOReportEvent> criticalErrors;
    private final List<OOReportEvent> topErrors;
    private final List<OOReportEvent> resurfacedErrors;
    private final List<OOReportEvent> allIssues;
    private final boolean unstable;
    private final RegressionInput input;
    private final long eventVolume;
    private final int uniqueEventsCount;
    private final boolean checkNewGate;
    private final boolean checkResurfacedGate;
    private final boolean checkCriticalGate;
    private final boolean checkVolumeGate;
    private final boolean checkUniqueGate;
    private final boolean checkRegressionGate;
    private final Integer maxEventVolume;
    private final Integer maxUniqueVolume;
    private final boolean markedUnstable;

    public QualityReport(RegressionInput input, List<OOReportRegressedEvent> regressions,
                         QualityGateReport qualityGateReport, boolean unstable,
                         OverOpsConfiguration config) {

        this.input = input;

        this.regressions = regressions;
        this.allIssues = new ArrayList<OOReportEvent>();
        this.newIssues = qualityGateReport.getNewErrors();
        this.criticalErrors = qualityGateReport.getCriticalErrors();
        this.topErrors = qualityGateReport.getTopErrors();
        this.resurfacedErrors = qualityGateReport.getResurfacedErrors();

        if (regressions != null) {
            allIssues.addAll(regressions);
        }

        this.eventVolume =  qualityGateReport.getTotalErrorCount();
        this.uniqueEventsCount =  qualityGateReport.getUniqueErrorCount();
        this.unstable = unstable;
        this.checkNewGate = config.isNewEvents();
        this.checkResurfacedGate = config.isResurfacedErrors();
        this.checkCriticalGate = input.criticalExceptionTypes != null && input.criticalExceptionTypes.size() > 0;
        this.checkVolumeGate = config.isMaxErrorVolume();
        this.checkUniqueGate = config.isMaxUniqueErrors();
        this.checkRegressionGate = config.isRegressionPresent();
        this.maxEventVolume =  config.getMaxErrorVolume();
        this.maxUniqueVolume = config.getMaxUniqueErrors();
        this.markedUnstable = config.isMarkUnstable();
    }

    public RegressionInput getInput() {
        return input;
    }

    public List<OOReportEvent> getResurfacedErrors() {
        return resurfacedErrors;
    }

    public List<OOReportEvent> getAllIssues() {
        return allIssues;
    }

    public List<OOReportEvent> getCriticalErrors() {
        return criticalErrors;
    }

    public List<OOReportEvent> getTopErrors() {
        return topErrors;
    }

    public List<OOReportEvent> getNewIssues() {
        return newIssues;
    }

    public List<OOReportRegressedEvent> getRegressions() {
        return regressions;
    }

    public long getUniqueEventsCount() {
        return uniqueEventsCount;
    }

    public boolean getUnstable() {
        return unstable;
    }

    public long getEventVolume() {
        return eventVolume;
    }

    public boolean isCheckNewGate() {
        return checkNewGate;
    }

    public boolean isCheckResurfacedGate() {
        return checkResurfacedGate;
    }

    public boolean isCheckCriticalGate() {
        return checkCriticalGate;
    }

    public boolean isCheckVolumeGate() {
        return checkVolumeGate;
    }

    public boolean isCheckUniqueGate() {
        return checkUniqueGate;
    }

    public boolean isCheckRegressionGate() {
        return checkRegressionGate;
    }

    public Integer getMaxEventVolume() {
        return maxEventVolume;
    }

    public Integer getMaxUniqueVolume() {
        return maxUniqueVolume;
    }

    public boolean isMarkedUnstable() {
        return markedUnstable;
    }

    public String getDeploymentName() {
        if (Objects.nonNull(getInput()) && Objects.nonNull(getInput().deployments)) {
            String value = getInput().deployments.toString();
            value = value.replace("[", "");
            value = value.replace("]", "");
            return value;
        }
        return "";
    }
}
