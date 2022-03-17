
package mofcomposer;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.midi.MidiUnavailableException;
import org.jfugue.midi.MidiFileManager;
import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;
import org.jfugue.*;
import org.jfugue.realtime.RealtimePlayer;
import org.jfugue.theory.*;

/**
 *
 * @author MOF
 */
public class Composer
{
    String[] durations = {"w","h","q","i","s","t","x","o"};
    String[] durations_desc = {"o","x","t","s","i","q","h","w"};
    int[] duration_bins = {128, 64, 32, 16, 8, 4, 2, 1};
    int[] duration_bins_desc = {1, 2, 4, 8, 16, 32, 64, 128};
    String[] instruments = {"[Piano]", "[Flute]", "[Guitar]", "33", "[Tubular_Bells]"};
        
    //microtones of Turkish maqam music
    String[] mts = {
    "281.60",
    "294.11",
    "308.92",
    "319.55",
    "328.57",
    "330.88",
    "348.23",
    "359.67",
    "362.62",
    "367.05",
    "373.85",
    "392.15",
    "411.49",
    "424.23",
    "427.80",
    "439.99",
    "463.85",
    "481.41",
    "491.13",
    "496.32",
    "522.87",
    "537.43",
    "546.29",
    "550.58"
    };
    String[] microtones = {
    "281.606416318516",
    "294.11764735126997",
    "308.92910331717906",
    "319.55838832720576",
    "328.5703534842914",
    "330.8823525651737",
    "348.2373199782753",
    "359.6713879512031",
    "362.6251322276069",
    "367.0588230280748",
    "373.8554413435828",
    "392.15686275902397",
    "411.4959590574865",
    "424.239772936163",
    "427.8074863385694",
    "439.99999983288757",
    "463.8548111631015",
    "481.4143789271389",
    "491.1306294284758",
    "496.3235294117646",
    "522.8758173880346",
    "537.4310057235962",
    "546.294382896056",
    "550.5882356701202"
    };
    
    RealtimePlayer realtimeplayer;
    Player player;
    public Composer()
    {
        Locale.setDefault(new Locale("en"));
        //player = new Player();
    }
    
    public void Destroy()
    {
        if(realtimeplayer!=null)
        {
            realtimeplayer.close();
        }
    }
    
    public String RhythmLinesToLayers(String rhythmstring)
    {
        String rt = "V9";
        String[] strsplit = rhythmstring.split("\n");
        int layerno = 0;
        for (String rhymstr : strsplit)
        {
            rt += " L"+layerno+" "+rhymstr+"";
            layerno++;
        }
        return rt;
    }
    
    public String GetRandomRhythm(int size)
    {
        String rt = "";
        int randdrumnote = 35;
        int loopleft = size;
        while(loopleft>0)
        {
            //35-81
            randdrumnote = GetRandomNumberInRange(0, 81);
            String randdrumnotestr = ""+randdrumnote;
            if(randdrumnote < 35)
            {
                randdrumnotestr = "R";
            }
            rt += ""+randdrumnotestr+"s ";
            loopleft -= 8;
        }
        rt = ClearLastSpace(rt);        
        return rt;
    }
    
    public int GetRandomNumberInRange(int min, int max)
    {
        if (min >= max)
        {
            max = min;
        }
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }
    
    public void PlayNotes(String s)
    {
        s = NewLinesToSpaces(s);
        System.out.println("Playing: !"+s+"!");
        if(player!=null)
        {
            player = null;
        }
        player = new Player();
        player.play(s);
    }
    
    public void PlayNotesInRealtime(String s)
    {
        s = NewLinesToSpaces(s);
        Pattern p = new Pattern(s);
        Pattern atomizedp = p.atomize();
        //System.out.println("Playing in realtime: !"+s+"!");
        try
        {
            if(realtimeplayer!=null)
            {
                //realtimeplayer.close();
            }
            else
            {
                realtimeplayer = new RealtimePlayer();
            }
            realtimeplayer.play(atomizedp);
        }
        catch (MidiUnavailableException ex)
        {
            
        }
    }
    public void StopRealtimePlayer()
    {
        if(realtimeplayer!=null)
        {
            realtimeplayer.close();
        }
    }
    /*public void PlayNotesMulti(String... s)
    {
        System.out.println("Playing: !"+s+"!");
        try
        {
            if(player!=null)
            {
                player.close();
            }
            player = new RealtimePlayer();
            //player.play(s);
        }
        catch (MidiUnavailableException ex)
        {
            
        }
    }*/
    public void SaveNotes(String s, String filename)
    {
        String composeTxt = s;
        s = NewLinesToSpaces(s);
        Pattern pattern = new Pattern(s);
        try
        {
            File f = new File("C:\\tmp\\JMidi\\"+filename+".mid");
            OutputStream outstr = new FileOutputStream(f);
            MidiFileManager.savePatternToMidi(pattern, outstr);
            outstr.close();
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }
    public void SaveNotesTxt(String s, String filename)
    {
        String composeTxt = NewLinesToWindowNewLines(s);
        try
        {
            File f2 = new File("C:\\tmp\\JMidi\\"+filename+".txt");
            OutputStream outstr2 = new FileOutputStream(f2);
            outstr2.write(composeTxt.getBytes());
            outstr2.close();
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }
    
    public String GetRandomFileName()
    {
        return "JFugue_"+String.valueOf(System.currentTimeMillis());
    }
    
    public String List2Str(String[] pat)
    {
        String r = "";
        for (String s : pat)
        {
            r += ""+s+" ";
        }
        return r;
    }
    
    public String ChordProgression()
    {
        //I V vi IV
        ChordProgression r = new ChordProgression("I-III-IV-iv").setKey("E");
        //r = r.setKey("Abmaj").eachChordAs("$!w");
                
        System.out.println("--> "+r);
        return r.getPattern().toString();
    }
    
    public String MicrotonalPattern()
    {
        String p = "A4w m440w m400w m400w m400w m390w m380w m370w m360w";
       // String p = "m"+GetMicrotone(1)+"w m"+GetMicrotone(5)+"w m"+GetMicrotone(9)+"w m"+GetMicrotone(11)+"w m"+GetMicrotone(15)+"w";
        return p;
    }
    
    public String GetMicrotone(int n)
    {
        String r = mts[n];
        String[] rs = r.split("\\.");
        System.out.println("X "+rs[0]);
        return rs[0];
    }
    
    
    public String TrimWhitespaces(String str)
    {
        String[] strsplit = str.split("\\s+",2);
        return strsplit[0];
    }
    public String TrimChordNumber(String str)
    {
        String[] strsplit = str.split("\\s+",2);
        return strsplit[0];
    }
    
    public String GetStringChordNotes(String strchord)
    {
        String[] chords = strchord.split("\\s+");
        String chordnotes = "";
        for (String chord : chords)
        {
            Chord thechord = new Chord(strchord);
            Note[] notes = thechord.getNotes();
            for (Note note : notes)
            {
                chordnotes = chordnotes+note.getToneString()+" ";
            }
        }
        if(chordnotes.length() > 0)
        {
            chordnotes = chordnotes.substring(0, chordnotes.length()-1);
        }
        return chordnotes;
    }
    
    
    public String GetStringChordNotes2(String strchord)
    {
        String[] thechord_split = strchord.split("/");
        String thechord = thechord_split[0];
        thechord = thechord.replace("(", "").replace(")", "").replace("+", " ");
        return thechord;
    }
    
    public String DurationLetterToNumber(String strdur)
    {
        String rt = "";
        switch (strdur)
        {
            case "w":
                rt = "1.0";
                break;            
            case "h":
                rt = "0.5";
                break;            
            case "q":
                rt = "0.25";
                break;            
            case "i":
                rt = "0.125";
                break;            
            case "s":
                rt = "0.0625";
                break;            
            case "t":
                rt = "0.03125";
                break;            
            case "x":
                rt = "0.015625";
                break;
            case "o":
                rt = "0.0078125";
                break;
        }
        return rt;
    }
    public int DurationLetterToStepNumber(String strdur)
    {
        int rt = 0;
        switch (strdur)
        {
            case "w":
                rt = 128;
                break;            
            case "h":
                rt = 64;
                break;            
            case "q":
                rt = 32;
                break;            
            case "i":
                rt = 16;
                break;            
            case "s":
                rt = 8;
                break;            
            case "t":
                rt = 4;
                break;            
            case "x":
                rt = 2;
                break;
            case "o":
                rt = 1;
                break;
        }
        return rt;
    }
    public int DurationNumberToStepNumber(String durnum)
    {
        int rt = 0;
        switch (durnum)
        {
            case "1.0":
                rt = 128;
                break;            
            case "0.5":
                rt = 64;
                break;            
            case "0.25":
                rt = 32;
                break;            
            case "0.125":
                rt = 16;
                break;            
            case "0.0625":
                rt = 8;
                break;            
            case "0.03125":
                rt = 4;
                break;            
            case "0.015625":
                rt = 2;
                break;
            case "0.0078125":
                rt = 1;
                break;
        }
        return rt;
    }
    public String StepNumberToDurationLetter(int stno)
    {
        String rt = "";
        switch (stno)
        {
            case 128:
                rt = "w";
                break;            
            case 64:
                rt = "h";
                break;            
            case 32:
                rt = "q";
                break;            
            case 16:
                rt = "i";
                break;            
            case 8:
                rt = "s";
                break;            
            case 4:
                rt = "t";
                break;            
            case 2:
                rt = "x";
                break;
            case 1:
                rt = "o";
                break;
        }
        return rt;
    }
    
    public String GetSingleChordDurationNumber(String strchord)
    {
        String[] splitchord = strchord.split("/");
        return splitchord[1];
    }
    public String GetSingleChordDuration(String strchord)
    {
        strchord = TrimWhitespaces(strchord);
        if(strchord.length() < 1)
        {
            return "";
        }
        return strchord.substring(strchord.length()-1,strchord.length());
    }
    
    public String GetChordName(String chordpattern)
    {
        chordpattern = TrimChordNumber(chordpattern);
        ChordProgression cp = new ChordProgression(chordpattern);
        String chordname = cp.toString();
        if(chordname.toLowerCase().contains("min"))
        {
            chordname = chordname.toLowerCase();
        }
        return chordname;
    }
    public String GetChordNotes(String chordpattern)
    {
        chordpattern = TrimChordNumber(chordpattern);
        ChordProgression cp = new ChordProgression(chordpattern);
        String chordnotes = "";
        Chord[] chords = cp.getChords();
        for (Chord chord : chords)
        {
            Note[] notes = chord.getNotes();
            for (Note note : notes)
            {
                chordnotes = chordnotes+note+" ";
            }
        }
        chordnotes = ClearLastSpace(chordnotes);
        return chordnotes;
    }
    public ArrayList<String> GetChordNotesAsList(String chordpattern)
    {
        Chord c = new Chord(chordpattern);
        ArrayList<String> chordnotes = new ArrayList<>();
        
        Note[] notes = c.getNotes();
        for (Note note : notes)
        {
            if(!chordnotes.contains(note.toString()))
            {
                chordnotes.add(note.toString());
            }
        }
        
        return chordnotes;
    }
    
    public void PlayChords(String chordpattern)
    {
        PlayChords(chordpattern, "w");
    }
    public void PlayChords(String chordpattern, String duration)
    {
        ChordProgression cp = new ChordProgression(chordpattern);
        cp.allChordsAs("$!"+duration);
        PlayNotes(cp.toString());
    }
    
    
    public void SaveChords(String chordpattern)
    {
        SaveChords(chordpattern, "w");
    }
    public void SaveChords(String chordpattern, String duration)
    {
        ChordProgression cp = new ChordProgression(chordpattern);
        cp.allChordsAs("$!"+duration);
        SaveNotes(cp.getPattern().toString(), GetRandomFileName());
    }
    
    public String Axiom(ArrayList<String> a)
    {
        int n = 0;
        String pattern = "";//T120 
        for (String s : a)
        {
            pattern = pattern+"V"+n+" I"+GetInstrument(n)+" "+s+" ";
            n++;
        }
        return pattern;
    }
    
    
    public String AxiomLines(ArrayList<String> a)
    {
        int n = 0;
        String pattern = "";//T120 
        for (String s : a)
        {
            pattern = pattern+"V"+n+" I"+GetInstrument(n)+" "+s+""+"\n";
            n++;
        }
        return pattern;
    }
    
    
    public String ListToLines(ArrayList<String> a)
    {
        int n = 0;
        String pattern = "";//T120 
        for (String s : a)
        {
            pattern += ""+s+"\n";
            n++;
        }
        return pattern;
    }
    
    public String AxiomLinesFromLinedPattern(String lines)
    {
        int n = 0;
        String pattern = "";//T120 
        String[] splitlines = lines.split("\n");
        for (String s : splitlines)
        {
            if(s.replace(" ", "").equals(""))
            {
                continue;
            }
            pattern = pattern+"V"+n+" I"+GetInstrument(n)+" "+s+""+" ";
            n++;
        }
        pattern = ClearLastSpace(pattern);
        return pattern;
    }
    
    public String[] AxiomArray(ArrayList<String> a)
    {
        String[] pattern = new String[a.size()];
        for (int i=0; i<a.size(); i++)
        {
            pattern[i] = "V"+i+" I"+GetInstrument(i)+" "+a.get(i)+"";
        }
        return pattern;
    }
    
    
    public ArrayList<String> GenerateRandomFromChords(String pattern, String scalenotes)
    {
        ArrayList<String> ax = new ArrayList<>();
        
        String a_chords = "";
        String a_keys ="";
        String a_arpeg0 = "";
        String a_keys2 ="";
        String[] splitchords = pattern.split("\\s+");
        
        int blocksize = 128;
        String[] duratsinters = RandomDurationAndIntervals(blocksize);
        System.out.println("Scale notes: "+scalenotes);
        System.out.println("Random durations: "+duratsinters[0]);
        System.out.println("Random intervals: "+duratsinters[1]);
        
        for (int i = 0; i < splitchords.length; i++)
        {
            String splitchord = splitchords[i];
            String splitchord_next = splitchord;
            if(i+1 < splitchords.length)
            {
                splitchord_next = splitchords[i+1];
            }
            
            
            a_chords += ""+splitchord+" ";
            
            String notesofthischord_only = GetStringChordNotes2(splitchord);
            
            String notesofthischord = notesofthischord_only;
            notesofthischord += " "+ GetStringChordNotes2(splitchord_next);
            String durationofthischord = GetSingleChordDurationNumber(splitchord);
            int thisduration = DurationNumberToStepNumber(durationofthischord);
            
            
            
            //setup arpeggios here
            //count blocksize VARIABLE
            //Write This!
            String arprootnote = GetRandomNote(notesofthischord_only);//Select root of arpeggio
            String[] duratintpart = GetDurationAndIntervalPart(duratsinters, thisduration);
            String arpegnotes = IntervalStartingFrom(scalenotes, arprootnote, duratintpart[1]);
            String arpegnoteswithdurations = CombineNotesAndDurations(arpegnotes, duratintpart[0]);
            a_arpeg0 += arpegnoteswithdurations+" ";
            /*System.out.println("$$$$$$$$$$$$");
            System.out.println("intval part "+duratintpart[1]);
            System.out.println("durat part "+duratintpart[0]);
            System.out.println("root note: "+arprootnote);
            System.out.println("arpegnotes: "+arpegnotes);
            System.out.println("+++"+arpegnoteswithdurations+"+++");*/
            
            
            
            int maxstep = thisduration;
            while(maxstep > 0)
            {
                int durbinind = GetRandomDurationBin(maxstep);
                int thisstep = duration_bins_desc[durbinind];
                //System.out.println("thisstep: "+thisstep);
                String randomduration = DurationBinToLetter(thisstep);
                
                String randomnote = GetRandomNote(notesofthischord);
                randomnote = GetNoteLetter(randomnote)+""+(GetNoteOctave(randomnote)+1);
                
                //a_keys += ""+randomnote+""+randomduration+" ";
                a_keys += ""+randomnote+"/"+DurationLetterToNumber(randomduration)+" ";
                //System.out.println(""+DurationLetterToNumber(randomduration));
                
                maxstep -= thisstep;
            }
            
            
            
            
            
            
            int maxstep2 = thisduration;
            while(maxstep2 > 0)
            {
                int durbinind = GetRandomDurationBin(maxstep2);
                int thisstep = duration_bins_desc[durbinind];
                //System.out.println("thisstep: "+thisstep);
                String randomduration = DurationBinToLetter(thisstep);
                //System.out.println("dict: #"+scalenotes+"#");
                String randomnote = GetRandomNote(scalenotes);
                randomnote = GetNoteLetter(randomnote)+""+(GetNoteOctave(randomnote)+1);
                
                //a_keys += ""+randomnote+""+randomduration+" ";
                a_keys2 += ""+randomnote+"/"+DurationLetterToNumber(randomduration)+" ";
                //System.out.println(""+DurationLetterToNumber(randomduration));
                
                maxstep2 -= thisstep;
            }
            
            
            
            
            
        }
        System.out.println("#"+a_chords+"#");
        a_chords = ClearLastSpace(a_chords);
        a_keys = ClearLastSpace(a_keys);
        a_arpeg0 = ClearLastSpace(a_arpeg0);
        a_keys2 = ClearLastSpace(a_keys2);
        
        ax.add(a_chords);
        ax.add(a_keys);
        ax.add(a_arpeg0);
        ax.add(a_keys2);
        
        return ax;
    }
    
    public String IntervalStartingFrom(String scaleitems, String rootnote, String intervalpart)
    {
        return IntervalStartingFrom(scaleitems, rootnote, intervalpart, 0);
    }
    public String IntervalStartingFrom(String scaleitems, String rootnote, String intervalpart, int relativeoctave)
    {
        String rt = "";
        //System.out.println("-------"+scaleitems+" @ "+rootnote);
        //scaleitems = ClearLastSpace(scaleitems);
        String[] scaleitems_split = scaleitems.split("\\s+");
        int[] itemoctaves = new int[scaleitems_split.length];
        for (int i = 0; i < scaleitems_split.length; i++)
        {
            itemoctaves[i] = GetNoteOctave(scaleitems_split[i]);
            scaleitems_split[i] = GetNoteLetter(scaleitems_split[i]);
        }
        
        Note onlyrootnote = new Note(rootnote);
        int positioninoctave = onlyrootnote.getOctave();
        rootnote = onlyrootnote.getToneString().replace(String.valueOf(positioninoctave), "");
        
        
        int rootnoteind = 0;
        for (int i = 0; i < scaleitems_split.length; i++)
        {
            if(scaleitems_split[i].toLowerCase().equals(rootnote.toLowerCase()))
            {
                rootnoteind = i;
                break;
            }
        }
        String[] intervalpal_split = intervalpart.split("\\s+");
        
        int lastnoteind = rootnoteind;
        rt += ""+scaleitems_split[lastnoteind]+""+(itemoctaves[lastnoteind]+relativeoctave)+" ";
        //System.out.println("RT : "+rt);
        for (int i = 1; i < intervalpal_split.length; i++)
        {
            int thisinterval = Integer.parseInt(intervalpal_split[i]);
            lastnoteind += thisinterval;
            //System.out.println("A "+lastnoteind);
            
            if(lastnoteind < 0)
            {
                int lastnodeind2 = Math.abs(lastnoteind);
                lastnoteind = scaleitems_split.length - (lastnodeind2 % scaleitems_split.length);
                relativeoctave--;
            }
            //System.out.println("B "+lastnoteind);
            
            if(lastnoteind > scaleitems_split.length-1)
            {
                lastnoteind = (lastnoteind % scaleitems_split.length);
                relativeoctave++;
            }
            
            //System.out.println("C c  "+itemoctaves[lastnoteind]+" + "+relativeoctave);
            rt += ""+scaleitems_split[lastnoteind]+""+(itemoctaves[lastnoteind]+relativeoctave)+" ";
        }
        
        rt = ClearLastSpace(rt);
        
        return rt;
    }
    
    public String CombineNotesAndDurations(String notes, String durations)
    {
        String rt = "";
        if(notes.isEmpty())
        {
            return rt;
        }
        Pattern pat = new Pattern(notes);
        //System.out.println("["+pat.toString()+"]["+durations+"]");
        rt = pat.addToEachNoteToken(durations).toString();        
        return rt;
    }
    
    public String[] RandomDurationAndIntervals(int step)
    {
        String[] rt = {"","0 "};//0: durations, 1: intervals
        
        int lastdistancetoroot = 0;
        int totalstep = step;
        while(totalstep > 0)
        {
            int randomstep = GetRandomDurationBin(totalstep);
            int thisstep = duration_bins_desc[randomstep];
            
            String randomduration = DurationBinToLetter(thisstep);
            rt[0] += randomduration+" ";
            
            int randominterval = GetRandomInterval(7, lastdistancetoroot);//make it 2 maybe
            lastdistancetoroot += randominterval;
            rt[1] += ""+randominterval+" ";
            
            totalstep -= thisstep;
        }
        
        rt[0] = ClearLastSpace(rt[0]);
        rt[1] = ClearLastSpace(rt[1]);
        
        String[] oldintervallist = rt[1].split("\\s+");
        String newintervallist = "";
        for (int i = 0; i < oldintervallist.length-1; i++)
        {
            newintervallist += oldintervallist[i]+" ";
        }
        newintervallist = ClearLastSpace(newintervallist);
        rt[1] = newintervallist;
        
        return rt;
    }
    
    public String GetDurationAt(String[] duratsinters_, int index_)
    {
        String rt = "w";
        
        String[] splitstr = duratsinters_[0].split("\\s+");
        if(index_ < splitstr.length)
        {
            rt = splitstr[index_];
        }
        
        return rt;
    }
    public String GetIntervalAt(String[] duratsinters_, int index_)
    {
        String rt = "0";
        
        String[] splitstr = duratsinters_[1].split("\\s+");
        if(index_ < splitstr.length)
        {
            rt = splitstr[index_];
        }
        
        return rt;
    }
    
    
    public String[] GetDurationAndIntervalPart(String[] duratsinters_, int len)
    {
        String[] rt = {"",""};
        
        int totallong = 0;
        String[] splitstr = duratsinters_[0].split("\\s+");
        String[] splitstr2 = duratsinters_[1].split("\\s+");
        for (int i = 0; i < splitstr.length; i++)
        {
            if(totallong == len)
            {
                break;
            }
            int thissteplong = DurationLetterToStepNumber(splitstr[i]);
            int totallongbefore = totallong;
            totallong += thissteplong;
            if(totallong > len)
            {
                int stepdif = totallong-len;
                System.out.println("Fazlalık: "+totallong+"-"+len+" = "+stepdif+" ... "+totallongbefore+"+"+thissteplong);
                int neededsteps = len-totallongbefore;
                
                String newdurstr = "";
                for (int j = 0; j < duration_bins.length; j++)
                {
                    if(neededsteps - duration_bins[j] < 0)
                    {
                        continue;
                    }
                    neededsteps -= duration_bins[j];
                    newdurstr += ""+durations[j];
                    if(neededsteps==0)
                    {
                        break;
                    }
                }
                splitstr[i] = newdurstr;
                
                System.out.println("Fazlalık giderildi: "+newdurstr+" gerekli kalan: "+neededsteps);
                totallong = len;
                //break;
            }
            
            if(i >= len)
            {
                len /= 2;
                i = 0;
                //break;
            }
            rt[0] += splitstr[i]+" ";
            rt[1] += splitstr2[i]+" ";
        }
        
        rt[0] = ClearLastSpace(rt[0]);
        rt[1] = ClearLastSpace(rt[1]);
        
        return rt;
    }
    
    public int GetRandomInterval(int max, int distancetoroot)
    {
        Random generator = new Random();
        int randomint = generator.nextInt(max);
        
        while( Math.abs(randomint + distancetoroot) > 6 )
        {
            randomint = generator.nextInt(max);
            if(generator.nextBoolean())
            {
                randomint *= -1;
            }
        }
        return randomint;
    }
    
    public String GetRandomNote(String pattern)
    {
        String rt = "";
        Random generator = new Random();
        String[] splitpattern = pattern.split("\\s+");
        if(splitpattern.length > 0)
        {
            rt = splitpattern[generator.nextInt(splitpattern.length)];
        }
        return rt;
    }
    
    
    public int GetRandomDurationBin(int maxdur)
    {
        int strdur = 1;
        Random generator = new Random();
        int maxselectable_dur_index = 0;
        for (int i = 0; i < duration_bins_desc.length; i++)
        {
            if(duration_bins_desc[i] > maxdur)
            {
                break;
            }
            maxselectable_dur_index = i;
        }
        strdur = generator.nextInt(maxselectable_dur_index+1);
        while(strdur < 3)
        {
            strdur = generator.nextInt(maxselectable_dur_index+1);
        }
        return strdur;
    }
    
    
    public String DurationBinToLetter(int durbin)
    {
        String strdur = "";
        for (int i = 0; i < durations.length; i++)
        {
            if(duration_bins[i] == durbin)
            {
                strdur = durations[i];
                break;
            }
        }
        return strdur;
    }
    public int DurationLetterToBin(String durletter)
    {
        int strdur = 0;
        for (int i = 0; i < durations.length; i++)
        {
            if(durations[i].equals(durletter))
            {
                strdur = duration_bins[i];
                break;
            }
        }
        return strdur;
    }
    
    public ArrayList<String> GenerateRandomAxiom(HashMap<String, ArrayList<String>> chordsAndNotes)
    {
        ArrayList<String> ax = new ArrayList<>();
        
        String a_chords = "";
        String a_keys = "";
        //System.out.println(""+chordsAndNotes);
        Random generator = new Random();
        Object[] values = chordsAndNotes.keySet().toArray();
        for (int i = 0; i < 8; i++)
        {
            String randomKey = (String)values[generator.nextInt(values.length)];
            ArrayList<String> randomValues = chordsAndNotes.get(randomKey);
            System.out.println("####");
            System.out.println(""+randomKey);
            System.out.println(""+randomValues);
            a_chords += ""+randomKey+"w ";
            for (int j = 0; j < 3; j++)
            {
                int vindex = generator.nextInt(randomValues.size());
                String randomVal = randomValues.get(vindex);
                String duration = "q";
                if(j==2)
                {
                    duration ="h";
                }
                a_keys += ""+randomVal+""+duration+" ";
            }
        }
        
        ax.add(a_chords);
        ax.add(a_keys);
        
        return ax;
    }
    
    public String GetInstrument(int n)
    {
        return instruments[(n%instruments.length)];
    }
    
    public String ClearLastSpace(String txt)
    {
        String rt = "";
        if(txt.length() < 1)
        {
            return txt;
        }
        String lastchar = txt.substring(txt.length()-1, txt.length());
        if(lastchar.equals(" "))
        {
            rt = txt.substring(0, txt.length()-1);
        }
        return rt;
    }
    
    public String NewLinesToSpaces(String txt)
    {
        return txt.replace("\n", " ").replace("\r", "");
    }
    
    public String NewLinesToWindowNewLines(String txt)
    {
        return txt.replace("\n", "\r\n");
    }
    
    public String GetNoteLetter(String note)
    {
        Note onlyrootnote = new Note(note);
        int positioninoctave = onlyrootnote.getOctave();
        String onlynoteletter = onlyrootnote.getToneString().replace(String.valueOf(positioninoctave), "");
        return onlynoteletter;
    }
    public int GetNoteOctave(String note)
    {
        Note thenote = new Note(note);
        int octave = thenote.getOctave();
        return octave;
    }
    
    public String SpaceToPlus(String s)
    {
        String rt = "";
        String[] s_split = s.split("\\s+");
        
        for (String ss : s_split)
        {
            rt += ""+ss+"+";
        }
        if(rt.length() > 0)
        {
            rt = rt.substring(0, rt.length()-1);
        }
        return rt;
    }
    
    //interval must begin with '0 ': "0 3"
    public String ShiftNote(String scaleitems, String rootnote, String interval)
    {
        String shiftednote = IntervalStartingFrom(scaleitems, rootnote, interval);
        String[] shiftednote_split = shiftednote.split("\\s+");
        return GetNoteLetter(shiftednote_split[1]);
    }
    
}
