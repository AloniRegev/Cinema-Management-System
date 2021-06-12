package il.ac.haifa.cs.sweng.cms.common.entities;

import java.io.Serializable;
import java.util.*;

import javax.persistence.*;

@Entity
@Table(name = "screenings")

/**
 * Screening Entity
 */
public class Screening implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="movie")
	private Movie movie;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="theater_id")
	private Theater theater;

	private GregorianCalendar date;
	@OneToMany(targetEntity = Ticket.class, mappedBy = "screening",fetch = FetchType.LAZY)
	private List<Ticket> tickets;

	/**
	 * constructors
	 */
	public Screening() {
		this.movie = new Movie();
		this.theater = new Theater();
		this.date = new GregorianCalendar();
		this.setTickets(new ArrayList<Ticket>(theater.getSeatsCapacity()));
	}

	public Screening(Movie movie, Theater theater, GregorianCalendar gregorianCalendar)
	{
		this();
		this.movie = movie;
		this.theater = theater;
		this.theater.setRealSeatsCapacity();
		this.date = gregorianCalendar;
//		this.setTickets(new ArrayList<Ticket>(this.theater.getSeatsCapacity()));
//		for(int i=0;i<this.theater.getSeatsCapacity();i++)
//			tickets.set(i, new Ticket(this,i));
	}

	/**
	 * Get screening ID.
	 * @return Screening ID.
	 */
	public int getId() { return id; }


	/**
	 * Movie getter and setter.
	 */
	public Movie getMovie() { return movie; }
	public void setMovie(Movie movie) { this.movie = movie; }

	/**
	 * Theater getter and setter.
	 */
	public Theater getTheater() { return theater; }
	public void setTheater(Theater theater) { this.theater = theater; this.theater.setRealSeatsCapacity(); }

	/**
	 * Date getter and setter.
	 */
	public GregorianCalendar getDate() { return date; }
	public void setDate(GregorianCalendar date) { this.date = date; }

	/**
	 * Tickets getter and setter.
	 */
	public List<Ticket> getTickets() {return tickets;}
	public void setTickets(ArrayList<Ticket> tickets) {this.tickets = tickets;}

	/**
	 * Ticket choose and unchoose.
	 */
	public void chooseTicket(Customer customer,int seat,boolean isPackage){
		this.tickets.get(seat).setCustomer(customer);
		customer.addTicket(this.tickets.get(seat),isPackage);
	}

	public void unChooseTicket(int seat){
		this.tickets.get(seat).setCustomer(null);
	}

	/**
	 * Set ID.
	 * @param id ID to set.
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Set tickets.
	 * @param tickets tickets to set.
	 */
	public void setTickets(List<Ticket> tickets) {
		this.tickets = tickets;
	}

}
