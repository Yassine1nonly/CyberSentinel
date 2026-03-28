package com.socmonitor.model;

import java.util.List;

public class Alert {
    private String id;
    private String title;
    private String description;
    private String severity;   // CRITICAL, HIGH, MEDIUM, LOW
    private String status;     // NEW, ACKNOWLEDGED, RESOLVED
    private String sourceIp;
    private String destinationIp;
    private String ruleName;
    private String timestamp;
    private String host;
    private String category;
    private List<String> evidence;
    private String mitreTechnique;
    private int count;

    public Alert(String id, String title, String description, String severity,
                 String status, String sourceIp, String destinationIp,
                 String ruleName, String timestamp, String host,
                 String category, List<String> evidence, String mitreTechnique, int count) {
        this.id = id; this.title = title; this.description = description;
        this.severity = severity; this.status = status; this.sourceIp = sourceIp;
        this.destinationIp = destinationIp; this.ruleName = ruleName;
        this.timestamp = timestamp; this.host = host; this.category = category;
        this.evidence = evidence; this.mitreTechnique = mitreTechnique; this.count = count;
    }

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

    public void setStatus(String status) { this.status = status; }
}
