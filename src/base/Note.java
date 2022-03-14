package base;

import java.util.Date;
import java.util.Objects;

public class Note implements Comparable<Note>,java.io.Serializable{
	private Date date;
	private String title;
	
	public Note(String title) {
		this.title = title;
		date = new Date(System.currentTimeMillis());
	}
	
	public String getTitle() {
		return title;
	}
	
	public String toString() {
		return date.toString() + "\t" + title;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(title);
	}

	@Override
	public boolean equals(Object obj) {
		Note other = (Note) obj;
		return Objects.equals(title, other.title);
	}
	
	@Override
	public int compareTo(Note o) {
		if(this.date.after(o.date))
			return 1;
		if(this.date.before(o.date))
			return -1;
		return 0;
	}
	
}
