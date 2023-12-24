package com.accolite.Payment.model;


import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="User_table")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(unique = true)
    private String userId;
    private String userSecret;
    private boolean userEnrolled;
    private boolean isApproved;
    private boolean offlinePaymentsEnabled;
    private LocalDateTime offlinePaymentsDisabledTime;

    private Double latitude;

    private Double longitude;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "wallet_id", referencedColumnName = "id")
    private Wallet wallet;

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    @Column(name = "enrollment_timestamp")
    private LocalDateTime enrollmentTimestamp;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserSecret() {
        return userSecret;
    }

    public void setUserSecret(String userSecret) {
        this.userSecret = userSecret;
    }

    public boolean isUserEnrolled() {
        return userEnrolled;
    }

    public void setUserEnrolled(boolean userEnrolled) {
        this.userEnrolled = userEnrolled;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }

    public LocalDateTime getEnrollmentTimestamp() {
        return enrollmentTimestamp;
    }

    public void setEnrollmentTimestamp(LocalDateTime enrollmentTimestamp) {
        this.enrollmentTimestamp = enrollmentTimestamp;
    }

    public boolean isOfflinePaymentsEnabled() {
        return offlinePaymentsEnabled;
    }

    public void setOfflinePaymentsEnabled(boolean offlinePaymentsEnabled) {
        this.offlinePaymentsEnabled = offlinePaymentsEnabled;
    }

    public LocalDateTime getOfflinePaymentsDisabledTime() {
        return offlinePaymentsDisabledTime;
    }

    public void setOfflinePaymentsDisabledTime(LocalDateTime offlinePaymentsDisabledTime) {
        this.offlinePaymentsDisabledTime = offlinePaymentsDisabledTime;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
