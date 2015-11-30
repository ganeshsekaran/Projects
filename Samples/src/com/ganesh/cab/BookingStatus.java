package com.ganesh.cab;

public class BookingStatus {
	
	private final BookingRequest request;
	
	BookingStatus(BookingRequest request) {
		this.request = request;
	}

	public enum Status {
		PENDING_CONFIRMATION, CONFIRMED, DECLINED, CANCELLED, CAB_NOT_AVAILABLE, AT_SERVICE, COMPLETED
	}

	volatile Status status;

	public Status getStatus() {
		return status;
	}

	void setStatus(Status status) {
		this.status = status;
	}
	
	public void cancelRequest(){
		if(status.equals(Status.CONFIRMED) || status.equals(Status.PENDING_CONFIRMATION)){
			BookingManager.getInstance().cancelRequest(request);
		}
	}
	
	public void waitForCompletion(){
		if(status.equals(Status.CONFIRMED) || status.equals(Status.AT_SERVICE)){
			synchronized (this) {
				try {
					this.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
