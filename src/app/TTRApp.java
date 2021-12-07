package app;
import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import controller.TTRController;

/*

Names & Roles:
Lukas (50%) (total)
- FileImportController (100%) (created)
- TTRController (100%) (created)
- RouteController (80%) (created)
- TicketController (70%) (created)
- TrainCardController (60%) (created)
- CardPanel (90%) (created)
- GameFrame (100%) (created)
- PlayerPanel (85%) (created)
- ScorePanel (50%) (edited)
- TTRApp (25%) (we all made this class)


Jayden (22.5%) (total)
- RouteController (20%) (edited)
- TicketController (30%) (edited)
- TrainCardController (40%)  (edited)
- CardColour (100%) (created)
- PlayerColour (100%) (created)
- Path (100%) (created)
- PlayerPanel (15%) (edited)
- ScorePanel (50%) (created)
- BoardPanel (80%) (created)
- TTRApp (25%) (we all made this class)

Jaffer (20%) (total)
- City (100%) (created)
- Route (80%) (created)
- Player (90%) (created)
- Ticket (100%) (created)
- BoardPanel (20%) (edited)
- TrainCard (80%) (created)
- MouseListener (100%) (created)
- TTRApp (25%) (we all made this class)

John (7.5%) (total)
- Route (20%) (edited)
- TrainCard (20%) (edited)
- CardPanel (10%) (edited)
- Point (100%) (created)
- Player (10%) (edited)
- TTRApp (25%) (we all made this class)

Date: 2021-12-06

Course Code: ICS4U, Mr. Fernandes

Title: Ticket to Ride, Canadian Edition

Description: This project is a re-imagined version of the classic board game, Ticket to Ride. 
Normally, the game features a board of the USA. However, we've redesigned its map and points of interest for an entirely new experience.

Features:
- Sounds effects and background music
- Takes into account the longest path by a player
- Add an "extras" section with Instructions, developer notes, and other things

Major Skills: Used advanced data structures, algorithms, recursion through depth first searches, and drew on other parts of our knowledge both from the ICS4U course and Computing Club.

Areas of Concern:
- The number of trains left resets back to the default (45) after drawing a card from the card deck. The error is purely visual and does not affect the outcome of a game.
- The CardColour GRAY, which was labeled as the default colour, still shows up when displayed in the GUI. It cannot take any cards and is strictly a visual error.

 */

public class TTRApp {
	
	public static void main(String[]args) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
		
		new TTRController();
		
	}

}
