package restaurant.beans;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="wsreservation")
public class WSReservation {
	private String description;	
	private int participantsNumber;
	private String reservationDate;
	private String reservationTime;	
	private String title;
	private String userId;
	
	public void setDesription(String description)
	{
		this.description = description;
	}	
	public void setParticipantsNumber(int participantsNumber)
	{
		this.participantsNumber = participantsNumber;
	}
	public void setReservationDate(String reservationDate)
	{
		this.reservationDate = reservationDate;
	}
	public void setReservationTime(String reservationTime)
	{
		this.reservationTime = reservationTime;
	}
	public void setTitle(String title)
	{
		this.title = title;
	}
	public void setUserId(String userId)
	{
		this.userId = userId;
	}
	
	
	public String getDesription()
	{
		return description;
	}
	public int getParticipantsNumber()
	{
		return participantsNumber;
	}
	public String getReservationDate()
	{
		return reservationDate;
	}
	public String getReservationTime()
	{
		return reservationTime;
	}
	public String getTitle()
	{
		return title;
	}
	public String getUserId()
	{
		return userId;
	}	
	
	@Override
    public String toString() {
        return "Rezervation{" + 
        	"description=" + description + 
        	", participantsNumber="+ participantsNumber +
        	", reservationDate="+reservationDate+
        	", reservationTime=" + reservationTime + 
        	", title=" + title + 
        	", userId=" + userId + 
        	"}";
    }
}

