package savesystem;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.io.FileUtils;

import util.ObjectHolder;

public class SaveSystem {

	private final String backupDirectory;
	private final String worldDirectory;
	
	private final int worldArraySize;
	
	private List<ObjectHolder<File>> savesList;
	
	public SaveSystem(int worldArraySize, String worldDirectory, String backupDirectory) {
		this.worldArraySize = worldArraySize;
		this.backupDirectory = backupDirectory;
		this.worldDirectory = worldDirectory;
		this.savesList = new ArrayList<>();
		
		File existingBackups[] = getSubfilesAndChildDirectories(backupDirectory);
		
		int i = 0;
		for (File file : existingBackups) {
			if (i > worldArraySize)
				break;
			i++;
			savesList.add(new ObjectHolder<>(file));
		}
	}
	
	public ObjectHolder<File> save() throws IOException {
		File backedUpWorldDirectory = new File(backupDirectory + '\\' + worldDirectory.substring(worldDirectory.lastIndexOf('\\')) + '_' + System.currentTimeMillis());
		backedUpWorldDirectory.mkdir();
		savesList.add(new ObjectHolder<>(backedUpWorldDirectory));
		
		File interiorWorldFiles[] = getSubfilesAndChildDirectories(worldDirectory, "session.lock");
		for (File file : interiorWorldFiles) {

			File copiedFile = new File(backedUpWorldDirectory.getPath() + '\\' + file.getName());
			
			if (file.isDirectory()) {
				FileUtils.copyDirectory(file, copiedFile);
			} else {
				FileUtils.copyFile(file, copiedFile);
			}
		}

		if (savesList.size() > worldArraySize) {
			FileUtils.forceDelete(savesList.get(savesList.size() - 1).get());
		}
		return new ObjectHolder<>(backedUpWorldDirectory);
	}
	
	private File[] getSubfilesAndChildDirectories(File parent, String... exclusions) {
		String existingSubNames[] = parent.list();
		List<File> existingSubs = new ArrayList<>();
		
		for (String subfileName : existingSubNames) {
			boolean equalsExclusion = false;
			
			for (String exclusionFile : exclusions) {
				if (subfileName.equals(exclusionFile)) {
					equalsExclusion = true;
					break;
				}
			}
			if (!equalsExclusion) {
				existingSubs.add(new File(parent.getPath() + '\\' + subfileName));
			}
		}
		
		Collections.sort(existingSubs, Comparator.comparing(File::getPath));
		
		int size = existingSubs.size();
		File asArray[] = new File[size];
		for (int i = 0; i < size; i++)
			asArray[i] = existingSubs.get(i);
			
		return asArray;
	}
	
	private File[] getSubfilesAndChildDirectories(String parentName, String... exclusions) {
		return getSubfilesAndChildDirectories(new File(parentName), exclusions);
	}
}