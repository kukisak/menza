package restaurant.beans;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Reservation {
	private String description;
	private int id;
	private int participantsNumber;
	private String reservationDate2;
	private String reservationTime;
	private int statusId;
	private int tableId;
	private String title;
	private String userId;
	
	public void setDesription(String description)
	{
		this.description = description;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public void setParticipantsNumber(int participantsNumber)
	{
		this.participantsNumber = participantsNumber;
	}
	public void setReservationDate2(String reservationDate2)
	{
		this.reservationDate2 = reservationDate2;
	}
	public void setReservationTime(String reservationTime)
	{
		this.reservationTime = reservationTime;
	}
	public void setStatusId(int statusId)
	{
		this.statusId = statusId;
	}
	public void setTableId(int tableId)
	{
		this.tableId = tableId;
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
	public int getId()
	{
		return id;
	}
	public int getParticipantsNumber()
	{
		return participantsNumber;
	}
	public String getReservationDate2()
	{
		return reservationDate2;
	}
	public String getReservationTime()
	{
		return reservationTime;
	}
	public int getStatusId()
	{
		return statusId;
	}
	public int getTableId()
	{
		return tableId;
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
        	", id=" + id + 
        	", participantsNumber="+ participantsNumber +
        	", reservationDate2="+reservationDate2+
        	", reservationTime=" + reservationTime + 
        	", statusId=" + statusId + 
        	", tableId=" + tableId + 
        	", title=" + title + 
        	", userId=" + userId + 
        	"}";
    }
	
}

