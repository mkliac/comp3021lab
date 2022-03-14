package base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Folder implements Comparable<Folder>,java.io.Serializable{
	private ArrayList<Note> notes;
	private String name;
	
	public Folder(String name) {
		this.name = name;
		notes = new ArrayList<Note>();
	}
	
	public void addNote(Note note) {
		notes.add(note);
	}
	
	public String getName() {
		return name;
	}
	
	public ArrayList<Note> getNotes() {
		return notes;
	}
	
	public void sortNotes() {
		Collections.sort(notes);
	}
	
	public List<Note> searchNotes(String keywords){
		List<Note> re = new ArrayList<Note>();
		List<List<String>> keywordsPieces = new ArrayList<List<String>>();
		List<String> parsekeywords = new ArrayList<String>();
		String t = "";
		for(int i = 0; i < keywords.length(); i++) {
			if(keywords.charAt(i) != ' ')
				t += keywords.charAt(i);
			if((i == keywords.length()-1 || keywords.charAt(i) == ' ') && t.length() > 0) {
				parsekeywords.add(t);
				t = "";
			}
		}
		for(int i = 0; i < parsekeywords.size();) {
			keywordsPieces.add(new ArrayList<String>());
			keywordsPieces.get(keywordsPieces.size()-1).add(parsekeywords.get(i).toLowerCase());
			i++;
			while(i < parsekeywords.size() && (parsekeywords.get(i).equals("OR") || parsekeywords.get(i).equals("or"))) {
				i++;
				keywordsPieces.get(keywordsPieces.size()-1).add(parsekeywords.get(i).toLowerCase());
				i++;
			}
		}
		for(int i = 0; i < notes.size(); i++) {
			boolean tf1 = true;
			for(int j = 0; j < keywordsPieces.size(); j++) {
				boolean tf2 = false;
				for(int k = 0; k < keywordsPieces.get(j).size(); k++) {
					if(notes.get(i).getTitle().toLowerCase().contains(keywordsPieces.get(j).get(k)) ||
					   notes.get(i) instanceof TextNote && ((TextNote)notes.get(i)).content.toLowerCase().contains(keywordsPieces.get(j).get(k))) {
						tf2 = true;
						break;
					}
				}
				if(tf2 == false) {
					tf1 = false;
					break;
				}
			}
			if(tf1 == true)
				re.add(notes.get(i));
		}
		return re;
	}
	@Override
	public boolean equals(Object obj) {
		Folder other = (Folder) obj;
		return Objects.equals(name, other.name);
	}
	
	@Override
	public String toString() {
		int nText = 0;
		int nImage = 0;
		for(Note note : notes) {
			if(note instanceof TextNote)
				nText++;
			else if(note instanceof ImageNote)
				nImage++;
		}
		return name + ":" + nText + ":" + nImage;
	}
	
	@Override
	public int compareTo(Folder o) {
		return this.name.compareTo(o.name);
	}
}
