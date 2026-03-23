package com.socmonitor.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Alert {

    public enum Severity { CRITICAL, HIGH, MEDIUM, LOW, INFO }
    public enum Status    { NEW, ACKNOWLEDGED, RESOLVED, FALSE_POSITIVE }

    @SerializedName("id")
    private String id;

    @SerializedName("title")
    private String title;

    @SerializedName("description")
    private String description;

    @SerializedName("severity")
    private String severity;   // "CRITICAL", "HIGH", etc.

    @SerializedName("status")
    private String status;     // "NEW", "ACKNOWLEDGED", etc.

    @SerializedName("source_ip")
    private String sourceIp;

    @SerializedName("destination_ip")
    private String destinationIp;

    @SerializedName("rule_name")
    private String ruleName;

    @SerializedName("timestamp")
    private String timestamp;

    @SerializedName("host")
    private String host;

    @SerializedName("category")
    private String category;   // "Malware", "Intrusion", "Auth Failure", etc.

    @SerializedName("evidence")
    private List<String> evidence;

    @SerializedName("mitre_technique")
    private String mitreTechnique;

    @SerializedName("count")
    private int count;

    // ── Constructors ──────────────────────────────────────────────────────────

    public Alert() {}

    /** Convenience constructor used by the mock data generator */
    public Alert(String id, String title, String description, String severity,
                 String status, String sourceIp, String destinationIp,
                 String ruleName, String timestamp, String host,
                 String category, List<String> evidence,
                 String mitreTechnique, int count) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.severity = severity;
        this.status = status;
        this.sourceIp = sourceIp;
        this.destinationIp = destinationIp;
        this.ruleName = ruleName;
        this.timestamp = timestamp;
        this.host = host;
        this.category = category;
        this.evidence = evidence;
        this.mitreTechnique = mitreTechnique;
        this.count = count;
    }

    // ── Getters ───────────────────────────────────────────────────────────────

    public String getId()              { return id; }
    public String getTitle()           { return title; }
    public String getDescription()     { return description; }
    public String getSeverity()        { return severity; }
    public String getStatus()          { return status; }
    public String getSourceIp()        { return sourceIp; }
    public String getDestinationIp()   { return destinationIp; }
    public String getRuleName()        { return ruleName; }
    public String getTimestamp()       { return timestamp; }
    public String getHost()            { return host; }
    public String getCategory()        { return category; }
    public List<String> getEvidence()  { return evidence; }
    public String getMitreTechnique()  { return mitreTechnique; }
    public int getCount()              { return count; }

    // ── Setters ───────────────────────────────────────────────────────────────

    public void setId(String id)                       { this.id = id; }
    public void setTitle(String title)                 { this.title = title; }
    public void setDescription(String description)     { this.description = description; }
    public void setSeverity(String severity)           { this.severity = severity; }
    public void setStatus(String status)               { this.status = status; }
    public void setSourceIp(String sourceIp)           { this.sourceIp = sourceIp; }
    public void setDestinationIp(String destinationIp) { this.destinationIp = destinationIp; }
    public void setRuleName(String ruleName)           { this.ruleName = ruleName; }
    public void setTimestamp(String timestamp)         { this.timestamp = timestamp; }
    public void setHost(String host)                   { this.host = host; }
    public void setCategory(String category)           { this.category = category; }
    public void setEvidence(List<String> evidence)     { this.evidence = evidence; }
    public void setMitreTechnique(String m)            { this.mitreTechnique = m; }
    public void setCount(int count)                    { this.count = count; }
}
