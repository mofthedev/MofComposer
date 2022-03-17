/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mofcomposer;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.midi.MidiUnavailableException;
import org.jfugue.midi.MidiFileManager;
import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;
import org.jfugue.realtime.RealtimePlayer;
import org.jfugue.rhythm.Rhythm;
import org.jfugue.theory.Chord;
import org.jfugue.theory.ChordProgression;
import org.jfugue.theory.Note;
import org.staccato.MicrotonePreprocessor;
import org.staccato.ReplacementMapPreprocessor;

/**
 *
 * @author MOF
 */
public class Test {
    public static void main(String[] args)
    {
        System.out.println("TEST9");
        
        
        Locale.setDefault(new Locale("en"));
        
        /*Rhythm rhythm = new Rhythm()
        .addLayer("O..oO...O..oOO..")
        .addLayer("..S...S...S...S.")
        .addLayer("````````````````")
        .addLayer("...............+");*/
        /*Rhythm rhythm = new Rhythm();
        rhythm.addLayer("....****....++++....*+*+*+*+....+*+*+*+*");
        Pattern p = rhythm.getPattern();
        System.out.println("Rhythm "+p);
        new Player().play(p);*/
            
        try
        {
            RealtimePlayer rplayer = new RealtimePlayer();
            Pattern p = new Pattern("(E4+G4+B4)w");
            Pattern p2 = new Pattern("(A4+C5+E5)w");
            
            rplayer.play(p);
            rplayer.play(p2);
        }
        catch (MidiUnavailableException ex)
        {
            ex.printStackTrace();
        }
        //new Player().play("V9 43s 44s 43s 44s 43s 44s");
//        new Player().play("(E4+G4+B4)w (A4+C5+E5)w (E4+G#4+B4)w");
       
        /*Note thenote = new Note("Eb6");
        int octave = thenote.getOctave();
        System.out.println("oct "+octave);*/
        
        //new Player().play("I[Flute] m294.117/1.0 m330.882/1.0 m367.058/1.0 m392.156/1.0");
        
        //new Player().play("I[Flute] m250.0/2.0 m500.0/2.0 m750.0/2.0");
        //new Player().play("I[Flute] 47/2.0 m254.6/2.0 50/2.0 m300.9/2.0 65/2.0 m701.8/2.0");
                          //127.3      150        350.9
                          //131.1      153.8      352.5
        /*String resultFromExpandingMicrotones = new MicrotonePreprocessor().preprocess("m254.6 m300.9 m701.8", null);
        System.out.println("Expanded microtones and got: "+resultFromExpandingMicrotones);
        System.out.println("************");
        String resultFromExpandingMicrotones2 = new MicrotonePreprocessor().preprocess("m367.058", null);//m123.47 185.205
        System.out.println("Expanded microtones and got: "+resultFromExpandingMicrotones2);
        */
        
        /*System.out.println("##########");//367.058
        System.out.println("357.058 : "+convertFrequencyToStaccato(357.058d));
        System.out.println("##########");//367.058
        System.out.println("367.058 : "+convertFrequencyToStaccato(347.058d));
        new Player().play("I[Flute] m367.058/1.0");
        */
        //SaveNotes("I[Flute] 47/2.0 m254.6/2.0 50/2.0 m300.9/2.0 65/2.0 m701.8/2.0");
        /*Note onlyrootnote = new Note("E6");
        System.out.println("x: "+onlyrootnote.getOctave());
        
        int positioninoctave = onlyrootnote.getOctave();
        String rootnote = onlyrootnote.getToneString().replace(String.valueOf(positioninoctave), "");
        System.out.println("y: "+rootnote);
        */
        /*try
        {
            RealtimePlayer player = new RealtimePlayer();
            player.play("T120 V0 I[Piano] A4min/1.0 E4min/1.0 V1 I[Flute] A4/0.5 C5/0.0625 G4/0.0625 B4/0.125 E5/0.0625 E4/0.0625 B4/0.0625 C5/0.0625 B4/0.0625 E4/0.0625 G4/0.5 G4/0.25 G4/0.125");
        
        }
        catch (MidiUnavailableException ex)
        {
            
        }*/
        //player.play("V0 I[Piano] Eq Ch. | Eq Ch. | Dq Eq Dq Cq   V1 I[Flute] Rw | Rw | GmajQQQ CmajQ");
        
        /*double total = 0.125+0.5+0.0625+0.03125+0.0078125+0.25+0.015625+0.0078125+0.0078125+0.0625+0.03125+0.015625+0.0078125+0.0078125+0.0078125+0.5+0.03125+0.25+0.0078125+0.03125+0.03125+0.0078125;
        System.out.println("Total: "+total);*/
        /*
        Player player = new Player();
        player.play("A4/0.25 A4/0.25 m440/0.25 m440/0.25 m400w m400w m390w m390w m380w m380w m370w m370w m360w m360w");
        */
        
        /*ChordProgression cp = new ChordProgression("I ii III IV V VI VII").setKey("C");
        NotesOfChord(cp);
        
        ChordProgression cp2 = new ChordProgression("I ii III IV V VI VII").setKey("E");
        NotesOfChord(cp2);
        
        Pattern p1 = new Pattern("T120 " +
                "V0 I[Piano] Eq Ch. | Eq Ch. | Dq Eq Dq Cq " + 
                "V1 I[Flute] Rw     | Rw     | GmajQQQ  CmajQ"
        );
        Player player = new Player();
        player.play(p1);
        SaveNotes(p1.toString());
        */
        // Specify the transformation rules for this Lindenmayer system
        /*Map rules = new HashMap() {{
              put("Cmajw", "Cmajw Fmajw");
              put("Fmajw", "Rw Bbmajw");
              put("Bbmajw", "Rw Fmajw");
              put("C5q", "C5q G5q E6q C6q");
              put("E6q", "G6q D6q F6i C6i D6q");
              put("G6i+D6i", "Rq Rq G6i+D6i G6i+D6i Rq");
              put("axiom", "axiom V0 I[Flute] Rq C5q V1 I[Tubular_Bells] Rq Rq Rq G6i+D6i V2 I[Piano] Cmajw E6q " +
                "V3 I[Warm] E6q G6i+D6i V4 I[Voice] C5q E6q");
        }};
*/
        // Set up the ReplacementMapPreprocessor to iterate 3 times
        // and not require brackets around replacements
        /*ReplacementMapPreprocessor rmp = ReplacementMapPreprocessor.getInstance();
        rmp.setReplacementMap(rules);
        rmp.setIterations(4);
        rmp.setRequireAngleBrackets(false);
*/
        // Create a Pattern that contains the L-System axiom
        /*String holaholahey = "T120 " 
                    + "V0 I[Flute] Rq C5q "
                    + "V1 I[Tubular_Bells] E5q Rq Rq G6i+D6i "
                    + "V2 I[Piano] Cmajw Eminq "
                    + "V3 I[Warm] E6q G6i+D6i "
                    + "V4 I[Voice] C5q E6q";
        System.out.println("!"+holaholahey+"!");
        Pattern axiom = new Pattern(holaholahey);
        System.out.println(axiom);
        //Player player = new Player();
        Player player = new Player();
        //System.out.println(rmp.preprocess(axiom.toString(), null));
        player.play(axiom);
        System.out.println("2. kez");
        //axiom = axiom.atomize();
        //System.out.println(axiom);
        Player player2 = new Player();
        player2.play(axiom);
*/

        //Player player = new Player();
        //player.play(cp);
        
        /*ChordProgression cp = new ChordProgression("");

        Chord[] chords = cp.setKey("C").getChords();
        for (Chord chord : chords)
        {
          System.out.print("Chord "+chord+" has these notes: ");
          Note[] notes = chord.getNotes();
          for (Note note : notes)
          {
            System.out.print(note+" , ");
          }
          System.out.println();
        }

        Player player = new Player();
        player.play(cp);
        */
    }
    
    public static String convertFrequencyToStaccato(double frequency)
    {
        double totalCents = 1200.0 * Math.log(frequency / 16.3515978312876) / Math.log(2);
        int octave = (int)(totalCents / 1200.0);
        double semitoneCents = totalCents - (octave * 1200.0);
        int semitone = (int)(semitoneCents / 100.0);
        double microtonalAdjustment = semitoneCents - (semitone * 100.0);
        int pitches = (int)(8192 + (microtonalAdjustment * 8192.0 / 100.0));
        System.out.println("microtonalAdjustment: "+microtonalAdjustment);
        System.out.println("semitoneCents | "+semitoneCents);
        // If we're close enough to the next note, just use the next note. 
        if (pitches >= 16380)
	{
        	pitches = 0;
        	semitone += 1;
        	if (semitone == 12)
		{
        		octave += 1;
        		semitone = 0;
        	}
        }

        int note = ((octave+1)*12)+semitone; // This gives a MIDI value, 0 - 128
        if (note > 127) note = 127;

        System.out.println("piko: "+pitches+" | note: "+note);
        StringBuilder buddy = new StringBuilder();
        if (pitches > 0)
	{
	        buddy.append(":PitchWheel(");
	        buddy.append((int)pitches);
	        buddy.append(") ");
        }
        buddy.append((int)note);
        if (pitches > 0)
	{
            buddy.append(" :PitchWheel(8192)"); // Reset the pitch wheel.  8192 = original pitch wheel position
        }
        return buddy.toString();
    }
    
    
    
    
    
    
    
    
    public static void SaveNotes(String s)
    {
        Pattern pattern = new Pattern(s);
        try
        {
            File f = new File("C:\\tmp\\JMidi\\JFugue_"+System.currentTimeMillis()+".mid");
            MidiFileManager.savePatternToMidi(pattern, f);
            
        }
        catch (IOException ex)
        {
            
        }
    }
    
    public static void NotesOfChord(ChordProgression cp)
    {
        System.out.println("*******");
        System.out.println(""+cp.toString());
        System.out.println("Pattern: "+cp.getPattern());
        System.out.println("###");

        Chord[] chords = cp.getChords();
        for (Chord chord : chords)
        {
          System.out.print("Chord "+chord+" has these notes: ");
          Note[] notes = chord.getNotes();
          for (Note note : notes)
          {
            System.out.print(note+" , ");
          }
          System.out.println();
        }
    }
}
