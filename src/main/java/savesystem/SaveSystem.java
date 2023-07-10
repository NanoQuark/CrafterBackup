package savesystem;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
		
		System.out.println(backupDirectory + '\n' + worldDirectory);
		
		String existingBackupNames[] = new File(backupDirectory).list();
		File existingBackups[] = new File[existingBackupNames.length];
		
		for (int k = 0; k < existingBackupNames.length; k++)
			existingBackups[k] = new File(existingBackupNames[k]);
		
		Arrays.sort(existingBackups, Comparator.comparing(File::getPath));
		
		int i = 0;
		for (File file : existingBackups) {
			if (i > worldArraySize)
				break;
			i++;
			savesList.add(new ObjectHolder<>(file));
		}
	}
	
	public ObjectHolder<File> save() throws IOException {
		File backedUpWorldFile = new File(backupDirectory + '/' + worldDirectory.substring(worldDirectory.lastIndexOf('/')) + '_' + System.currentTimeMillis());
		savesList.add(new ObjectHolder<>(backedUpWorldFile));
		FileUtils.copyDirectory(new File(worldDirectory), backedUpWorldFile);
		if (savesList.size() > worldArraySize) {
			FileUtils.forceDelete(savesList.get(savesList.size() - 1).get());
		}
		return new ObjectHolder<>(backedUpWorldFile);
	}
}