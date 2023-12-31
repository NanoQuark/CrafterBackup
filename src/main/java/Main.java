
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import savesystem.SaveSystem;
import util.ObjectHolder;

public final class Main {

	public static void main(String[] args) {

		SaveSystem ssOverworld = new SaveSystem(50, args[0] + "\\world", args[0] + "\\backup_world");
		SaveSystem ssNether = new SaveSystem(50, args[0] + "\\world_nether", args[0] + "\\backup_world_nether");
		SaveSystem ssEnd = new SaveSystem(50, args[0] + "\\world_the_end", args[0] + "\\backup_world_the_end");
		
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				ObjectHolder<File> backupOverworld = new ObjectHolder<>();
				ObjectHolder<File> backupNether = new ObjectHolder<>();
				ObjectHolder<File> backupEnd = new ObjectHolder<>();
				try {
					backupOverworld = ssOverworld.save();
					backupNether = ssNether.save();
					backupEnd = ssEnd.save();
				} catch (IOException e) {
					e.printStackTrace();
				}
				System.out.println("Backed Up the Overworld. Backup location: " + backupOverworld.get().getPath());
				System.out.println("Backed Up the Nether. Backup location: " + backupNether.get().getPath());
				System.out.println("Backed Up the End. Backup location: " + backupEnd.get().getPath());
			}
			
		}, 0L, 1000*60*30); // millis * seconds * minutes
		
	}

}
