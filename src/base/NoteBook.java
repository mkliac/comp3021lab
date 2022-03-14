package base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class NoteBook implements java.io.Serializable{
	private ArrayList<Folder> folders;
	private static final long serialVersionUID = 1L;
	
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
	
	public boolean save(String file) {
		FileOutputStream fos = null;
		ObjectOutputStream out = null;
		try {
			fos = new FileOutputStream(file);
			out = new ObjectOutputStream(fos);
			out.writeObject(this);
			out.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	
	public NoteBook(String file) {
		FileInputStream fis = null;
		ObjectInputStream in = null;
		try {
			fis = new FileInputStream(file);
			in = new ObjectInputStream(fis);
			NoteBook object = (NoteBook)in.readObject();
			in.close();
			folders = new ArrayList<Folder>();
			for(int i = 0; i < object.folders.size(); i++) {
				folders.add(object.folders.get(i));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
