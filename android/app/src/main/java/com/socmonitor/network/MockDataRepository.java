package com.socmonitor.network;

import com.socmonitor.model.Alert;
import com.socmonitor.model.DashboardStats;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;

public class MockDataRepository {

    private static MockDataRepository instance;
    private final List<Alert> alerts = new ArrayList<>();
    private final Random rnd = new Random();

    public static MockDataRepository getInstance() {
        if (instance == null) instance = new MockDataRepository();
        return instance;
    }

    private MockDataRepository() { seed(); }

    public List<Alert> getAlerts() { return new ArrayList<>(alerts); }

    public List<Alert> filterBySeverity(String severity) {
        if ("ALL".equals(severity)) return getAlerts();
        List<Alert> out = new ArrayList<>();
        for (Alert a : alerts)
            if (a.getSeverity().equalsIgnoreCase(severity)) out.add(a);
        return out;
    }

    public Alert getById(String id) {
        for (Alert a : alerts) if (a.getId().equals(id)) return a;
        return null;
    }

    public void acknowledge(String id) {
        Alert a = getById(id);
        if (a != null) a.setStatus("ACKNOWLEDGED");
    }

    public void resolve(String id) {
        Alert a = getById(id);
        if (a != null) a.setStatus("RESOLVED");
    }

    public DashboardStats getStats() {
        DashboardStats s = new DashboardStats();
        s.totalAlerts = alerts.size();
        for (Alert a : alerts) {
            switch (a.getSeverity()) {
                case "CRITICAL": s.criticalCount++; break;
                case "HIGH":     s.highCount++;     break;
                case "MEDIUM":   s.mediumCount++;   break;
                case "LOW":      s.lowCount++;      break;
            }
            if ("ACKNOWLEDGED".equals(a.getStatus())) s.acknowledgedCount++;
            if ("RESOLVED".equals(a.getStatus()))     s.resolvedCount++;
        }
        s.failedLogins24h      = 20 + rnd.nextInt(130);
        s.malwareDetections24h = rnd.nextInt(15);
        s.suspiciousIpsCount   = 5  + rnd.nextInt(35);
        s.threatLevel = s.criticalCount > 2 ? "CRITICAL" : s.highCount > 3 ? "HIGH" : "MEDIUM";
        s.lastUpdated = new SimpleDateFormat("HH:mm:ss", Locale.US).format(new Date());
        return s;
    }

    private void seed() {
        alerts.add(make("CRITICAL","NEW","Ransomware Detected",
            "WannaCry variant on WINSERVER-02. File encryption started.",
            "192.168.1.45","10.0.0.1","Ransomware Rule","WINSERVER-02","Malware",
            Arrays.asList("vssadmin.exe deletion","Mass .wcry rename","Shadow copy removed"),
            "T1486 - Data Encrypted",1));

        alerts.add(make("CRITICAL","NEW","Admin Brute Force",
            "452 failed logins on Administrator from 203.0.113.42 in 5 min.",
            "203.0.113.42","192.168.1.10","Brute Force Rule","DC-01","Auth Failure",
            Arrays.asList("452 failures / 300s","Account: Administrator","Geo: RU"),
            "T1110 - Brute Force",452));

        alerts.add(make("HIGH","NEW","C2 Communication",
            "Beaconing to known C2 server every 60 seconds.",
            "192.168.1.78","45.33.32.156","C2 Rule","WORKSTATION-14","Malware",
            Arrays.asList("Destination in threat intel","Beacon interval: 60s","Encrypted payload"),
            "T1071 - App Layer Protocol",28));

        alerts.add(make("HIGH","ACKNOWLEDGED","Privilege Escalation",
            "User jsmith attempted kernel exploit CVE-2023-0386.",
            "192.168.1.92","192.168.1.1","Privesc Rule","LINUX-SRV-03","Intrusion",
            Arrays.asList("CVE-2023-0386 exploit","sudo -l executed","New UID=0 process"),
            "T1068 - Exploitation",3));

        alerts.add(make("HIGH","NEW","Pass-the-Hash Attack",
            "NTLM hashes reused to authenticate across 4 hosts.",
            "192.168.1.45","192.168.1.0/24","PtH Rule","WINSERVER-02","Intrusion",
            Arrays.asList("NTLM without interactive login","Mimikatz signature","4 hosts targeted"),
            "T1550.002 - Pass the Hash",4));

        alerts.add(make("MEDIUM","NEW","Internal Port Scan",
            "SYN scan of 1024 ports from internal workstation.",
            "192.168.1.130","192.168.1.0/24","Port Scan Rule","WORKSTATION-07","Recon",
            Arrays.asList("1024 SYN packets/10s","Nmap signature","Internal source"),
            "T1046 - Network Scanning",1024));

        alerts.add(make("MEDIUM","ACKNOWLEDGED","PowerShell Obfuscation",
            "Encoded PowerShell command with download cradle detected.",
            "192.168.1.55","8.8.8.8","PS Rule","WORKSTATION-03","Execution",
            Arrays.asList("powershell.exe -enc ...","Base64 decoded: download","Network conn after"),
            "T1059.001 - PowerShell",1));

        alerts.add(make("MEDIUM","NEW","SSH Brute Force",
            "120 failed SSH logins from Tor exit node.",
            "185.220.101.5","10.0.0.50","SSH Rule","BASTION-01","Auth Failure",
            Arrays.asList("120 failures/10 min","Source: Tor exit","Multiple usernames"),
            "T1110.001 - Password Guessing",120));

        alerts.add(make("LOW","RESOLVED","Telnet Usage",
            "Plaintext Telnet traffic detected on internal segment.",
            "192.168.2.10","192.168.2.50","Protocol Rule","SWITCH-01","Policy",
            Arrays.asList("Port 23 traffic","Plaintext creds possible","Deprecated protocol"),
            "T1040 - Network Sniffing",5));

        alerts.add(make("LOW","NEW","Unknown USB Device",
            "Unregistered USB storage connected to finance workstation.",
            "192.168.1.88","-","USB Policy","WORKSTATION-FIN-02","Policy",
            Arrays.asList("VID:0951 PID:1666","No asset record","User: mlopez"),
            "T1091 - Removable Media",1));
    }

    private Alert make(String sev, String status, String title, String desc,
                       String src, String dst, String rule, String host,
                       String cat, List<String> evidence, String mitre, int count) {
        String id = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        long offset = (long)(rnd.nextInt(7200)) * 1000L;
        String ts = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US)
                .format(new Date(System.currentTimeMillis() - offset));
        return new Alert(id, title, desc, sev, status, src, dst, rule, ts, host, cat, evidence, mitre, count);
    }
}
