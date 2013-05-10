import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import org.newdawn.slick.SlickException;


public class LevelParser {
	public static HashMap<String, Room> parseLevel(String levelName) throws IOException, SlickException {
		HashMap<String, Room> level = new HashMap<String, Room>();
		BufferedReader r = new BufferedReader(new FileReader(Resources.LEVELS_FOLDER + levelName + "/lvl.txt"));
		String line;
		try {
			while ((line = r.readLine()) != null) {
				String[] lineData = line.split("\\s+");
				String id = lineData[0];
				String tmxPath = lineData[1];
				
				

				// create the room
				Room room = new Room(Resources.LEVELS_FOLDER + levelName + "/" + tmxPath, id);

				// parse the characters (if there are any)
				if (lineData.length == 3) {
					String[] charactersArray = lineData[2].split(",");
					
					// add the characters
					for (String characterLine : charactersArray) {
						String[] arr = characterLine.split(":");
						String type = arr[0];
						int count = Integer.parseInt(arr[1]);
						room.addCharacter(type, count);
					}
				}
				
				// add the room to the hash map
				level.put(id, room);
			}
		} catch (IOException e) {
			throw e;
		} catch (SlickException e) {
			throw e;
		} finally {
			r.close();
		}
		return level;
	}
}
