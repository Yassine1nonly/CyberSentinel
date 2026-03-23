package com.socmonitor.model;

import com.google.gson.annotations.SerializedName;

public class DashboardStats {

    @SerializedName("total_alerts")
    private int totalAlerts;

    @SerializedName("critical_count")
    private int criticalCount;

    @SerializedName("high_count")
    private int highCount;

    @SerializedName("medium_count")
    private int mediumCount;

    @SerializedName("low_count")
    private int lowCount;

    @SerializedName("acknowledged_count")
    private int acknowledgedCount;

    @SerializedName("resolved_count")
    private int resolvedCount;

    @SerializedName("failed_logins_24h")
    private int failedLogins24h;

    @SerializedName("malware_detections_24h")
    private int malwareDetections24h;

    @SerializedName("suspicious_ips_count")
    private int suspiciousIpsCount;

    @SerializedName("threat_level")
    private String threatLevel; // "LOW", "MEDIUM", "HIGH", "CRITICAL"

    @SerializedName("last_updated")
    private String lastUpdated;

    // Getters
    public int getTotalAlerts()          { return totalAlerts; }
    public int getCriticalCount()        { return criticalCount; }
    public int getHighCount()            { return highCount; }
    public int getMediumCount()          { return mediumCount; }
    public int getLowCount()             { return lowCount; }
    public int getAcknowledgedCount()    { return acknowledgedCount; }
    public int getResolvedCount()        { return resolvedCount; }
    public int getFailedLogins24h()      { return failedLogins24h; }
    public int getMalwareDetections24h() { return malwareDetections24h; }
    public int getSuspiciousIpsCount()   { return suspiciousIpsCount; }
    public String getThreatLevel()       { return threatLevel; }
    public String getLastUpdated()       { return lastUpdated; }

    // Setters
    public void setTotalAlerts(int v)          { totalAlerts = v; }
    public void setCriticalCount(int v)        { criticalCount = v; }
    public void setHighCount(int v)            { highCount = v; }
    public void setMediumCount(int v)          { mediumCount = v; }
    public void setLowCount(int v)             { lowCount = v; }
    public void setAcknowledgedCount(int v)    { acknowledgedCount = v; }
    public void setResolvedCount(int v)        { resolvedCount = v; }
    public void setFailedLogins24h(int v)      { failedLogins24h = v; }
    public void setMalwareDetections24h(int v) { malwareDetections24h = v; }
    public void setSuspiciousIpsCount(int v)   { suspiciousIpsCount = v; }
    public void setThreatLevel(String v)       { threatLevel = v; }
    public void setLastUpdated(String v)       { lastUpdated = v; }
}
