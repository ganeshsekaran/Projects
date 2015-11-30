package com.ganesh.cab;

public class BookingStatus {

	public enum Status {
		PENDING_CONFIRMATION, CONFIRMED, DECLINED, CANCELLED, CAB_NOT_AVAILABLE, AT_SERVICE
	}

	Status status;

	public Status getStatus() {
		return status;
	}

	void setStatus(Status status) {
		this.status = status;
	}

}
