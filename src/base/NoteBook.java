package base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NoteBook {
	private ArrayList<Folder> folders;
	
	public NoteBook() {
		folders = new ArrayList<Folder>();
	}
	
	public boolean createTextNote(String folderName, String title) {
		TextNote note = new TextNote(title);
		return insertNote(folderName, note);
	}
	
	public boolean createImageNote(String folderName, String title) {
		ImageNote note = new ImageNote(title);
		return insertNote(folderName, note);
	}
	
	public ArrayList<Folder> getFolders(){
		return folders;
	}
	
	public boolean insertNote(String folderName, Note note) {
		Folder f = null;
		for(Folder f1 : folders) {
			if(f1.getName().equals(folderName))
				f = f1;
		}
		if(f == null) {
			f = new Folder(folderName);
			folders.add(f);
		}
		for(Note n : f.getNotes()) {
			if(n.equals(note)) {
				System.out.println("Creating note " + note.getTitle() + " under folder " + folderName + " failed");
				return false;
			}
		}
		f.addNote(note);
		return true;
	}
	
	public void sortFolders() {
		for(int i = 0; i < folders.size(); i++)
			folders.get(i).sortNotes();
		Collections.sort(folders);
	}
	
	public List<Note> searchNotes(String keywords){
		List<Note> re = new ArrayList<Note>();
		for(int i = 0; i < folders.size(); i++) {
			List<Note> t = folders.get(i).searchNotes(keywords);
			for(int j = 0; j < t.size(); j++)
				re.add(t.get(j));
		}
		return re;
	}
	
	public boolean createTextNote(String folderName, String title, String content) {
		TextNote note = new TextNote(title, content);
		return insertNote(folderName, note);
	}
}
