package com.example.traveldb;
import java.sql.Date;
public class Trip {
	private int tripId;
    private String destination;
    private Date startDate;
    private Date endDate;
    private String transport;

    public Trip(int tripId, String destination, Date startDate, Date endDate, String transport) {
        this.tripId = tripId;
        this.destination = destination;
        this.startDate = startDate;
        this.endDate = endDate;
        this.transport = transport;
    }
    public int getTripId() { return tripId; }
    public String getDestination() { return destination; }
    public Date getStartDate() { return startDate; }
    public Date getEndDate() { return endDate; }
    public String getTransport() { return transport; }

    @Override
    public String toString() {
        return "Trip{" +
                "tripId=" + tripId +
                ", destination='" + destination + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", transport='" + transport + '\'' +
                '}';
    }
}
