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

/**
 * MockDataRepository – simulates a SIEM backend.
 * Replace the methods here with actual Retrofit API calls
 * once your real backend (Python/ELK, Wazuh, etc.) is ready.
 */
public class MockDataRepository {

    private static MockDataRepository instance;
    private final List<Alert> alerts = new ArrayList<>();
    private final Random rnd = new Random();

    public static MockDataRepository getInstance() {
        if (instance == null) instance = new MockDataRepository();
        return instance;
    }

    private MockDataRepository() {
        generateAlerts();
    }

    // ── Public API ────────────────────────────────────────────────────────────

    public List<Alert> getAlerts() {
        return new ArrayList<>(alerts);
    }

    public List<Alert> getAlertsBySeverity(String severity) {
        List<Alert> result = new ArrayList<>();
        for (Alert a : alerts) {
            if (a.getSeverity().equalsIgnoreCase(severity)) result.add(a);
        }
        return result;
    }

    public List<Alert> getAlertsByStatus(String status) {
        List<Alert> result = new ArrayList<>();
        for (Alert a : alerts) {
            if (a.getStatus().equalsIgnoreCase(status)) result.add(a);
        }
        return result;
    }

    public Alert getAlertById(String id) {
        for (Alert a : alerts) {
            if (a.getId().equals(id)) return a;
        }
        return null;
    }

    public boolean acknowledgeAlert(String id) {
        for (Alert a : alerts) {
            if (a.getId().equals(id)) {
                a.setStatus("ACKNOWLEDGED");
                return true;
            }
        }
        return false;
    }

    public boolean resolveAlert(String id) {
        for (Alert a : alerts) {
            if (a.getId().equals(id)) {
                a.setStatus("RESOLVED");
                return true;
            }
        }
        return false;
    }

    public DashboardStats getDashboardStats() {
        DashboardStats stats = new DashboardStats();
        stats.setTotalAlerts(alerts.size());
        int critical = 0, high = 0, medium = 0, low = 0, ack = 0, resolved = 0;
        for (Alert a : alerts) {
            switch (a.getSeverity().toUpperCase()) {
                case "CRITICAL": critical++; break;
                case "HIGH":     high++;     break;
                case "MEDIUM":   medium++;   break;
                case "LOW":      low++;      break;
            }
            if ("ACKNOWLEDGED".equalsIgnoreCase(a.getStatus())) ack++;
            if ("RESOLVED".equalsIgnoreCase(a.getStatus()))      resolved++;
        }
        stats.setCriticalCount(critical);
        stats.setHighCount(high);
        stats.setMediumCount(medium);
        stats.setLowCount(low);
        stats.setAcknowledgedCount(ack);
        stats.setResolvedCount(resolved);
        stats.setFailedLogins24h(rnd.nextInt(150) + 20);
        stats.setMalwareDetections24h(rnd.nextInt(15));
        stats.setSuspiciousIpsCount(rnd.nextInt(40) + 5);
        stats.setThreatLevel(critical > 3 ? "CRITICAL" : high > 5 ? "HIGH" : "MEDIUM");
        stats.setLastUpdated(now());
        return stats;
    }

    // ── Private helpers ───────────────────────────────────────────────────────

    private void generateAlerts() {
        alerts.add(make("CRITICAL", "NEW",
                "Ransomware Activity Detected",
                "WannaCry variant detected on WINSERVER-02. File encryption process started.",
                "192.168.1.45", "10.0.0.1",
                "Ransomware Detection Rule", "WINSERVER-02", "Malware",
                Arrays.asList("Process: svchost.exe → vssadmin.exe", "Shadow copy deletion attempt", "Mass file rename detected (.wcry)"),
                "T1486 - Data Encrypted for Impact", 1));

        alerts.add(make("CRITICAL", "NEW",
                "Brute Force - Admin Account",
                "452 failed login attempts on administrator account from 203.0.113.42 in 5 minutes.",
                "203.0.113.42", "192.168.1.10",
                "Brute Force Detection", "DC-01", "Auth Failure",
                Arrays.asList("452 failed logins in 300s", "Account: Administrator", "Geo: RU (unexpected)"),
                "T1110 - Brute Force", 452));

        alerts.add(make("HIGH", "NEW",
                "Malware C2 Communication",
                "Suspicious outbound traffic to known C2 server on port 443.",
                "192.168.1.78", "45.33.32.156",
                "C2 Communication Rule", "WORKSTATION-14", "Malware",
                Arrays.asList("Destination flagged in threat intel", "Beaconing interval: 60s", "Encrypted payload observed"),
                "T1071 - Application Layer Protocol", 28));

        alerts.add(make("HIGH", "ACKNOWLEDGED",
                "Privilege Escalation Attempt",
                "User 'jsmith' attempted to escalate privileges using known kernel exploit.",
                "192.168.1.92", "192.168.1.1",
                "Privilege Escalation", "LINUX-SRV-03", "Intrusion",
                Arrays.asList("Exploit: CVE-2023-0386", "sudo -l execution", "New UID=0 process spawned"),
                "T1068 - Exploitation for Privilege Escalation", 3));

        alerts.add(make("HIGH", "NEW",
                "Lateral Movement - Pass-the-Hash",
                "Pass-the-Hash attack detected. NTLM hashes used to authenticate to multiple hosts.",
                "192.168.1.45", "192.168.1.0/24",
                "Lateral Movement Detection", "WINSERVER-02", "Intrusion",
                Arrays.asList("NTLM auth without interactive login", "Mimikatz signature detected", "4 hosts targeted in 2 min"),
                "T1550.002 - Pass the Hash", 4));

        alerts.add(make("MEDIUM", "NEW",
                "Port Scan Detected",
                "SYN scan originating from internal host scanning 1024 ports.",
                "192.168.1.130", "192.168.1.0/24",
                "Port Scan Detection", "WORKSTATION-07", "Reconnaissance",
                Arrays.asList("1024 SYN packets / 10s", "Nmap signature match", "Internal source – investigate"),
                "T1046 - Network Service Scanning", 1024));

        alerts.add(make("MEDIUM", "ACKNOWLEDGED",
                "Suspicious PowerShell Execution",
                "Encoded PowerShell command executed with -EncodedCommand flag.",
                "192.168.1.55", "8.8.8.8",
                "PowerShell Obfuscation Rule", "WORKSTATION-03", "Execution",
                Arrays.asList("powershell.exe -enc JABz...", "Base64 decoded: download cradle", "Network connection after execution"),
                "T1059.001 - PowerShell", 1));

        alerts.add(make("MEDIUM", "NEW",
                "Failed SSH Login Surge",
                "120 failed SSH logins to bastion host from Tor exit node.",
                "185.220.101.5", "10.0.0.50",
                "SSH Brute Force Rule", "BASTION-01", "Auth Failure",
                Arrays.asList("120 failures in 10 min", "Source: Tor exit node", "Multiple usernames attempted"),
                "T1110.001 - Password Guessing", 120));

        alerts.add(make("LOW", "RESOLVED",
                "Insecure Protocol Usage",
                "Telnet traffic detected on internal network segment.",
                "192.168.2.10", "192.168.2.50",
                "Insecure Protocol Detection", "SWITCH-CORE-01", "Policy Violation",
                Arrays.asList("Telnet port 23 traffic", "Plaintext credentials possible", "Deprecated protocol in use"),
                "T1040 - Network Sniffing", 5));

        alerts.add(make("LOW", "NEW",
                "USB Device Inserted",
                "Unknown USB storage device connected to finance workstation.",
                "192.168.1.88", "-",
                "USB Insertion Policy", "WORKSTATION-FIN-02", "Policy Violation",
                Arrays.asList("VID: 0951, PID: 1666", "No asset record found", "User: mlopez"),
                "T1091 - Replication Through Removable Media", 1));
    }

    private Alert make(String severity, String status, String title, String desc,
                       String srcIp, String dstIp, String rule, String host,
                       String category, List<String> evidence,
                       String mitre, int count) {
        return new Alert(
                UUID.randomUUID().toString().substring(0, 8).toUpperCase(),
                title, desc, severity, status, srcIp, dstIp,
                rule, randomTimestamp(), host, category, evidence, mitre, count
        );
    }

    private String randomTimestamp() {
        long offset = (long)(rnd.nextInt(7200)) * 1000L; // up to 2h ago
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
                .format(new Date(System.currentTimeMillis() - offset));
    }

    private String now() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(new Date());
    }
}
