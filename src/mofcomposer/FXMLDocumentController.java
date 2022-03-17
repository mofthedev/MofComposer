/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mofcomposer;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;

/**
 *
 * @author MOF
 */
public class FXMLDocumentController implements Initializable {
    
    final String[] notes = {"C", "C#", "D", "D#", "E",  "F","F#","G","G#","A","A#","B"};
    final String[] chords = {"I", "i", "II", "ii", "III", "iii", "IV", "iv", "V", "v", "VI", "vi", "VII", "vii"};
    
    @FXML
    private ListView notelist, chordlist;
    
    @FXML
    private TextArea console, music, rhythm;
    
    @FXML
    private GridPane rhythmgrid;
    
    @FXML
    private ChoiceBox setchordtype, setoctave, setduration, setscale, chordnos;
    
    
    @FXML
    private void btn_listentonote_clicked(Event e)
    {
        e.consume();
        
        ObservableList<String> selectedItems =  notelist.getSelectionModel().getSelectedItems();
        String pattern = "";
        for(String s : selectedItems)
        {
            if(s==null){continue;}
            System.out.println("selected item " + s);
            pattern += ""+note2no(s,5)+"h Rh ";
        }
        //pattern = pattern.substring(0, pattern.length()-1);
        //System.out.println("!"+pattern+"!");
        //composer.SaveNotes(pattern);
        composer.PlayNotes(pattern);
    }
    
    @FXML
    private void btn_listentochord_clicked(Event e)
    {
        e.consume();
        
        
        ObservableList<String> selectedItems =  notelist.getSelectionModel().getSelectedItems();
        String pattern = "";
        String patterninfo = "";
        for(String s : selectedItems)
        {
            if(s==null){continue;}
            String notechord = note2chord(s,chordsettings[0], chordsettings[1], chordsettings[2]);
            pattern += ""+notechord+" ";
            patterninfo += "<"+notechord+"> ("+composer.GetStringChordNotes(notechord)+")\n";
        }
        composer.PlayNotes(pattern);
        
        WriteLn("Testing Chord Progress : "+patterninfo);
    }
    
    @FXML
    private void btn_listenchordno_clicked(Event e)
    {
        e.consume();
        
        

        
        String pattern = GetSelectedScaleChord("w");
        /*ObservableList<String> selectedItems =  notelist.getSelectionModel().getSelectedItems();
        String pattern = "";
        String patterninfo = "";
        for(String s : selectedItems)
        {
            if(s==null){continue;}
            String notechord = note2chord(s,chordsettings[0], chordsettings[1], chordsettings[2]);
            pattern += ""+notechord+" ";
            patterninfo += "<"+notechord+"> ("+composer.GetStringChordNotes(notechord)+")\n";
        }*/
        composer.PlayNotes(pattern);
        
        WriteLn("Testing Chord Progress : "+pattern);
    }
    
    
    @FXML
    private void btn_listentoscale_clicked(Event e)
    {
        e.consume();
        
        String scaleInterval = "0 "+selectedScale;
        String rootnoteofscale = notelist.getSelectionModel().getSelectedItem().toString();
        String allnotes = GetAllNotes();
        String scalepattern = composer.IntervalStartingFrom(allnotes, rootnoteofscale, scaleInterval);
        composer.PlayNotes(scalepattern);
        WriteLn(scalepattern);
        /*System.out.println("All notes: "+allnotes);
        System.out.println("Root note: "+rootnoteofscale);
        System.out.println("Scale interval: "+scaleInterval);
        System.out.println("Scale playing: "+scalepattern);*/
    }
    
    @FXML
    private void btn_addtochordlist_clicked(Event e)
    {
        e.consume();
        
        int ind = chordlist.getSelectionModel().getSelectedIndex();
        ObservableList<String> selectedItems =  notelist.getSelectionModel().getSelectedItems();
        for(String s : selectedItems)
        {
            if(s==null){continue;}
            //String newchordtoadd = note2chord(s,chordsettings[0],chordsettings[1],chordsettings[2]);
            //String newchordtoadd = GetSelectedScaleChord(chordsettings[2]);
            String newchordtoadd = ""+GetSelectedChordNo()+"/"+composer.DurationLetterToNumber(chordsettings[2]);
            WriteLn("New Chord <"+GetSelectedScaleChord(chordsettings[2])+">");
            AddToChordList(newchordtoadd, ind+1);
            chordlist.getSelectionModel().select(ind+1);
            ind++;
        }
    }
    
    @FXML
    private void btn_listentoallchords_clicked(Event e)
    {
        e.consume();
        
        
        ObservableList<String> listItems =  chordlist.getItems();
        String pattern = "";
        for(String s : listItems)
        {
            if(s==null){continue;}
            pattern += ""+GetChordByNo(s)+" ";
        }
        pattern = composer.ClearLastSpace(pattern);
        System.out.println("%"+pattern+"%");
        WriteLn("Playing Chord Progress : "+pattern);
        composer.PlayNotes(pattern);
        
    }
    
    
    
    @FXML
    private void btn_generate0_clicked(Event e)
    {
        e.consume();
        
        ObservableList<String> listItems =  chordlist.getItems();
        String pattern = "";
        String patterninfo = "";
        for(String s : listItems)
        {
            if(s==null){continue;}
            pattern += ""+GetChordByNo(s)+" ";
            //patterninfo += "<"+s+"> ("+composer.GetStringChordNotes(s)+")\n";
        }
        pattern = composer.ClearLastSpace(pattern);
        
        
        //get scale
        String rootnoteofscale = notelist.getSelectionModel().getSelectedItem().toString();
        String allowedscaleitems = GetScaleNotes(selectedScale, rootnoteofscale);
        
        
        
        ArrayList<String> randomaxiom = composer.GenerateRandomFromChords(pattern, allowedscaleitems);
        System.out.println(randomaxiom);
        String newaxiom = composer.ListToLines(randomaxiom);
        //newaxiom = newaxiom.substring(0, newaxiom.length()-1);
        
        MusicClear();
        MusicAdd(newaxiom);
        
        //System.out.println("Generated");
        LastMelody = newaxiom;
        
        WriteLn("Generated From Chord Progress : \n"+pattern);
    }
    
    @FXML
    private void btn_stopplayer_clicked(Event e)
    {
        e.consume();
        
        composer.StopRealtimePlayer();
    }
    
    @FXML
    private void btn_savelastmelody_clicked(Event e)
    {
        e.consume();
        
        String filename = composer.GetRandomFileName();
        composer.SaveNotesTxt(MusicGet(), filename);
        composer.SaveNotes(composer.AxiomLinesFromLinedPattern(MusicGet()), filename);
        
        WriteLn("Saved...");
    }
    
    
    @FXML
    private void btn_removeselectedchords_clicked(Event e)
    {
        e.consume();
        ObservableList<Integer> selectedItemIndices =  chordlist.getSelectionModel().getSelectedIndices();
        ObservableList<String> allItems =  chordlist.getItems();
        String[] itemsToKeep = new String[allItems.size()];
        for (int i=0; i<allItems.size(); i++)
        {
            itemsToKeep[i] = (String)allItems.get(i);
        }
        for(Integer s : selectedItemIndices)
        {
            if(s==null){continue;}
            if(s < 0){continue;}
            itemsToKeep[s.intValue()] = null;
        }
        chordsinlistview.clear();
        for (String item : itemsToKeep)
        {
            if(item==null){continue;}
            chordsinlistview.add(item);
        }
        //chordlist.setItems(chordsinlistview);
    }
    
    
    @FXML
    private void btn_listentomusic_clicked(Event e)
    {
        e.consume();
        
        composer.PlayNotes(composer.AxiomLinesFromLinedPattern(MusicGet()));
    }
    
    
    
    
    @FXML
    private void btn_microtonal_clicked(Event e)
    {
        e.consume();
        
        //composer.SaveNotes(composer.MicrotonalPattern());
        composer.PlayNotes(composer.MicrotonalPattern());
    }
    
    @FXML
    private void btn_makeitup_clicked(Event e)
    {
        e.consume();
        
        //composer.PlayNotes(composer.ChordProgression());
        
        ObservableList<String> selectedItems =  chordlist.getSelectionModel().getSelectedItems();
        //ArrayList<String> chords = new ArrayList<>();
        //ArrayList<String> notes = new ArrayList<>();
        
        HashMap<String, ArrayList<String>> chordnotes = new HashMap<>();
        for(String s : selectedItems)
        {
            if(s==null){continue;}
            String thecord = composer.GetChordName(composer.TrimChordNumber(s));
            //chords.add(thecord);
            if(!chordnotes.containsKey(thecord))
            {
                ArrayList<String> thenotes = composer.GetChordNotesAsList(thecord);
                chordnotes.put(thecord, thenotes);
            }
        }
        
        ArrayList<String> randomaxiom = composer.GenerateRandomAxiom(chordnotes);
        String newaxiom = composer.Axiom(randomaxiom);
        newaxiom = newaxiom.substring(0, newaxiom.length()-1);
        composer.PlayNotes(newaxiom);
        composer.SaveNotes(newaxiom, composer.GetRandomFileName());
        
    }
    
    
    
    
    @FXML
    private void btn_rhythm_generate(Event e)
    {
        e.consume();
        
        RhythmWrite(composer.GetRandomRhythm(128));
    }
    
    @FXML
    private void btn_rhythm_listen(Event e)
    {
        e.consume();
        String rhythm_str = RhythmGet();
        String rhythm_compiled = composer.RhythmLinesToLayers(rhythm_str);
        composer.PlayNotesInRealtime(rhythm_compiled);//PlayNotes//PlayNotesInRealtime
    }
    
    @FXML
    private void btn_rhythm_save(Event e)
    {
        e.consume();
        
        String rhythm_str = RhythmGet();
        String rhythm_compiled = composer.RhythmLinesToLayers(rhythm_str);
        
        System.out.println("#Compiled rhythm#");
        System.out.println(""+rhythm_compiled);
        
        String filename = composer.GetRandomFileName();
        composer.SaveNotesTxt(rhythm_str, filename);
        composer.SaveNotes(rhythm_compiled, filename);
        
        WriteLn("Rhythm Saved...");
    }
    
    
    
    StringProperty gridcell_active, gridcell_inactive;
    int[][] gridmatrix;
    
    private String LastMelody = "";
    private String[] chordsettings = {"min", "4", "w"};//chordtype, octave, duration
    
    private String[] scales_names = {   "locrian",          "minor",            "mixolydian",       "lydian",           "phrygian",         "dorian",           "major"};
    private String[] scales = {         "1 2 2 1 2 2 2",    "2 1 2 2 1 2 2",    "2 2 1 2 2 1 2",    "2 2 2 1 2 2 1",    "1 2 2 2 1 2 2",    "2 1 2 2 2 1 2",    "2 2 1 2 2 2 1"};
    private String selectedScale = "";
    
    private ObservableList<String> notesinlistview = FXCollections.observableArrayList();
    private ObservableList<String> chordsinlistview = FXCollections.observableArrayList();
    Composer composer;
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
        
        composer = new Composer();
        
        
        
        
        //ChoiceBox Chord Type
        setchordtype.getItems().add("maj");
        setchordtype.getItems().add("min");
        setchordtype.getSelectionModel().selectedItemProperty().addListener(new ChangeListener()
        {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue)
            {
                String newVal = newValue.toString();
                newVal = composer.TrimWhitespaces(newVal);
                chordsettings[0] = newVal;
            }
        });
        setchordtype.getSelectionModel().select(chordsettings[0]);
        
        //ChoiceBox Chord Octave
        setoctave.getItems().add("1");
        setoctave.getItems().add("2");
        setoctave.getItems().add("3");
        setoctave.getItems().add("4");
        setoctave.getItems().add("5");
        setoctave.getItems().add("6");
        setoctave.getItems().add("7");
        setoctave.getItems().add("8");
        setoctave.getItems().add("9");
        setoctave.getSelectionModel().selectedItemProperty().addListener(new ChangeListener()
        {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue)
            {
                String newVal = newValue.toString();
                newVal = composer.TrimWhitespaces(newVal);
                chordsettings[1] = newVal;
            }
        });
        setoctave.getSelectionModel().select(chordsettings[1]);
        
        //ChoiceBox Duration
        setduration.getItems().add("w");// 1.0
        setduration.getItems().add("h");// 0.5
        setduration.getItems().add("q");// 0.25
        setduration.getItems().add("i");// 0.125
        setduration.getItems().add("s");// 0.0625
        setduration.getItems().add("t");// 0.03125
        setduration.getItems().add("x");// 0.015625
        setduration.getItems().add("o");// 0.0078125
        setduration.getSelectionModel().selectedItemProperty().addListener(new ChangeListener()
        {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue)
            {
                String newVal = newValue.toString();
                newVal = composer.TrimWhitespaces(newVal);
                chordsettings[2] = newVal;
            }
        });
        setduration.getSelectionModel().select(chordsettings[2]);
        
        
        
        //ChoiceBox Scales
        for (String scales_name : scales_names)
        {
            setscale.getItems().add(scales_name);
        }
        setscale.getSelectionModel().selectedItemProperty().addListener(new ChangeListener()
        {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue)
            {
                String newScale = newValue.toString();
                for (int i=0; i<scales_names.length; i++)
                {
                    if(scales_names[i].equals(newScale))
                    {
                        selectedScale = scales[i];
                        break;
                    }
                }
            }
        });
        setscale.getSelectionModel().select(6);
        
        
        //Note list
        for (String note : notes)
        {
            notesinlistview.add(note);
            //System.out.println("Note "+note);
        }
        notelist.setItems(notesinlistview);
        notelist.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        notelist.getSelectionModel().selectedItemProperty().addListener(new ChangeListener()
        {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue)
            {
                if(newValue!=null)
                {
                    String newVal = newValue.toString();
                    String newchordtoadd = note2chord(newVal,chordsettings[0],chordsettings[1],chordsettings[2]);
                    WriteLn("Selected Chord <"+newchordtoadd+"> "+composer.GetChordNotesAsList(newchordtoadd)+"");
                }
            }
        });
        
        
        
        //ChoiceBox Chord Numbers
        for (int i = 0; i < 7; i++)
        {
            chordnos.getItems().add(""+i);
        }
        chordnos.getSelectionModel().select(0);
        
        
        //rhythm grid
        /*rhythmgrid.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event)
            {
                Node clickedNode = event.getPickResult().getIntersectedNode();
                if (clickedNode != rhythmgrid)
                {
                    // click on descendant node
                    Integer colIndex = GridPane.getColumnIndex(clickedNode);
                    Integer rowIndex = GridPane.getRowIndex(clickedNode);
                    System.out.println("Mouse clicked cell: " + colIndex + " , " + rowIndex);
                }
            }
        });*/
        int grid_w = rhythmgrid.getColumnConstraints().size();
        int grid_h = rhythmgrid.getRowConstraints().size();
        System.out.println("M = "+grid_h+","+grid_w);
        gridmatrix = new int[grid_w][grid_h];
        /*
        for (int col = 0 ; col < grid_w; col++ )
        {
            ColumnConstraints cc = new ColumnConstraints();
            cc.setFillWidth(true);
            cc.setHgrow(Priority.ALWAYS);
            rhythmgrid.getColumnConstraints().add(cc);
        }
        for (int row = 0 ; row < grid_h; row++ )
        {
            RowConstraints rc = new RowConstraints();
            rc.setFillHeight(true);
            rc.setVgrow(Priority.ALWAYS);
            rhythmgrid.getRowConstraints().add(rc);
        }*/
        
        gridcell_active = new SimpleStringProperty();
        gridcell_active.setValue("-fx-background-color: #7c4c9b");
        gridcell_inactive = new SimpleStringProperty();
        gridcell_inactive.setValue("-fx-background-color: #582f72");
        
        for (int i = 0; i < grid_w; i++)
        {
            for (int j = 0; j < grid_h; j++)
            {
                gridmatrix[i][j] = 0;
                
                Pane stackPane = new Pane();
                
                stackPane.styleProperty().bind(gridcell_inactive);
                //System.out.println("Created "+i+","+j);
                final int m_x = i;
                final int m_y = j;
                stackPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event)
                    {
                        //System.out.println("Matrix["+m_x+","+m_y+"]");
                        
                        if(gridmatrix[m_x][m_y]==0)
                        {
                            stackPane.styleProperty().bind(gridcell_active);
                            gridmatrix[m_x][m_y]=1;
                        }
                        else if(gridmatrix[m_x][m_y]==1)
                        {
                            stackPane.styleProperty().bind(gridcell_inactive);
                            gridmatrix[m_x][m_y]=0;
                        }
                        
                        RhythmTry(m_y);
                        
                        GridMatrixToRhythm();
                    }
                });
                
                
                rhythmgrid.add(stackPane, i, j);
            }
        }
        
        /*for (Node child : rhythmgrid.getChildren())
        {
            Integer column = GridPane.getColumnIndex(child);
            Integer row = GridPane.getRowIndex(child);
            if (column != null && row != null)
            {
                child.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event)
                    {
                        System.out.println("Matrix["+column+","+row+"]");
                    }
                });
            }
            //System.out.println("??&&");
        }*/
    }
    
    public void MatrixSet(int value, int x, int y)
    {
        gridmatrix[x][y] = value;
    }
    
    public int MatrixGet(int x, int y)
    {
        return gridmatrix[x][y];
    }
    
    public void shutdown()
    {
        composer.Destroy();
    }
    
    public void AddToChordList(String str)
    {
        AddToChordList(str, chordsinlistview.size());
    }
    public void AddToChordList(String str, int at)
    {
        chordsinlistview.add(at, str);
        chordlist.setItems(chordsinlistview);
        chordlist.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }
    
    
    
    
    
    public void WriteLn(String txtNew)
    {
        String txtOld = console.getText();
        console.setText(txtNew+"\n"+txtOld);
        //console.setText(txtOld+txtNew+"\n");
        //console.setScrollTop(Double.MAX_VALUE);
    }
    
    public void MusicAdd(String txtNew)
    {
        String txtOld = music.getText();
        music.setText(txtOld+txtNew+"\n");        
    }
    
    public void MusicClear()
    {
        music.setText("");
    }
    
    public String MusicGet()
    {
        String rt = "";
        String musicstr = music.getText();
        String[] musiclines = musicstr.split("\n");
        for (String musicline : musiclines)
        {
            if(musicline.charAt(0) != '-')
            {
                rt += musicline;
            }
            rt += "\n";
        }
        return rt;
    }
    
    
    
    public void RhythmWrite(String txtNew)
    {
        rhythm.setText(txtNew+"\n");        
    }
    public void RhythmAdd(String txtNew)
    {
        String txtOld = rhythm.getText();
        rhythm.setText(txtOld+txtNew+"\n");        
    }
    
    public void RhythmClear()
    {
        rhythm.setText("");
    }
    
    public String RhythmGet()
    {
        String rt = "";
        String musicstr = rhythm.getText();
        String[] musiclines = musicstr.split("\n");
        for (String musicline : musiclines)
        {
            if(musicline.charAt(0) != '-')
            {
                rt += musicline;
            }
            rt += "\n";
        }
        return rt;
    }
    
    int[] drumNotes = {35, 36, 38, 44, 49, 39, 37, 54};
    public void GridMatrixToRhythm()
    {
        String rhy = "";
        for (int j = 0; j < gridmatrix[0].length; j++)
        {
            for (int i = 0; i < gridmatrix.length; i++)
            {
                if(gridmatrix[i][j]==1)
                {
                    rhy += ""+drumNotes[j]+"s ";
                }
                else if(gridmatrix[i][j]==0)
                {
                    rhy += "Rs ";
                }
            }
            rhy = composer.ClearLastSpace(rhy);
            rhy += "\n";
        }
        RhythmWrite(rhy);
    }
    
    public void RhythmTry(int drumNote)
    {
        String rhythm_compiled = composer.RhythmLinesToLayers(""+drumNotes[drumNote]+"s");
        //System.out.println("Playing: "+rhythm_compiled);
        composer.PlayNotesInRealtime(rhythm_compiled);
    }
    
    
    
    
    public int note2no(String s)
    {
        return note2no(s, 5);
    }
    public int note2no(String s, int octave)
    {
        int r = 0;
        for (int i = 0; i < notes.length; i++)
        {
            if(s.equals(notes[i]))
            {
                r = i + octave*12;
                break;
            }
        }
        return r;
    }
    
    public String note2chord(String note, String chordtype)
    {
        return note2chord(note, chordtype, "4", "w");
    }
    public String note2chord(String note, String chordtype, String octaves)
    {
        return note2chord(note, chordtype, octaves, "w");
    }
    public String note2chord(String note, String chordtype, String octave, String duration)
    {
        return ""+note+""+octave+""+chordtype+"/"+composer.DurationLetterToNumber(duration);
    }
    
    public String GetAllNotes()
    {
        String rt = "";
        
        for (String thenote : notes)
        {
            rt += thenote+" ";
        }
        composer.ClearLastSpace(rt);
        return rt;
    }
    
    public String GetScaleNotes(String selectedScale, String rootnoteofscale)
    {
        String rt = "";
        
        //get scale interval
        String scaleInterval = "";
        String scaleInterval_ = "0 "+selectedScale;
        String[] scaleInterval_split = scaleInterval_.split("\\s+");
        for (int i = 0; i < scaleInterval_split.length-1; i++)
        {
            scaleInterval += ""+scaleInterval_split[i]+" ";
        }
        scaleInterval = composer.ClearLastSpace(scaleInterval);
        
        String allnotes = GetAllNotes();
        rt = composer.IntervalStartingFrom(allnotes, rootnoteofscale, scaleInterval);
        
        
        return rt;
    }

    private String GetSelectedScaleChord(String duration)
    {
        int chordfirstnoteno = Integer.valueOf(chordnos.getSelectionModel().getSelectedItem().toString());
        
        int selectednotecount = notelist.getSelectionModel().getSelectedItems().size();
        if(selectednotecount!=1)
        {
            return "";
        }
        //get scale
        String rootnoteofscale = notelist.getSelectionModel().getSelectedItem().toString();
        if(rootnoteofscale==null || rootnoteofscale.isEmpty()){return "";}
        String allowedscaleitems = GetScaleNotes(selectedScale, rootnoteofscale);
        
        String rootofchord = composer.ShiftNote(allowedscaleitems, rootnoteofscale, "0 "+chordfirstnoteno);
        String thechord = composer.IntervalStartingFrom(allowedscaleitems, rootofchord, "0 2 2", -1);
        
        String pattern = composer.SpaceToPlus(thechord);
        pattern = "("+pattern+")/"+composer.DurationLetterToNumber(duration);
        System.out.println("------------"+pattern);
        return pattern;
    }
    
    
    private String GetChordByNo(String chordwithduration)
    {
        String[] chordwithdur_split = chordwithduration.split("/");
        String chordnum = chordwithdur_split[0].replace("(", "").replace(")", "");
        String chorddur = chordwithdur_split[1];
        int chordfirstnoteno = Integer.valueOf(chordnum);
        
        //get scale
        String rootnoteofscale = notelist.getSelectionModel().getSelectedItem().toString();
        if(rootnoteofscale==null || rootnoteofscale.isEmpty()){return "";}
        String allowedscaleitems = GetScaleNotes(selectedScale, rootnoteofscale);
        
        String rootofchord = composer.ShiftNote(allowedscaleitems, rootnoteofscale, "0 "+chordfirstnoteno);
        String thechord = composer.IntervalStartingFrom(allowedscaleitems, rootofchord, "0 2 2", -1);
        
        String pattern = composer.SpaceToPlus(thechord);
        pattern = "("+pattern+")/"+chorddur;
        System.out.println("------------"+pattern);
        return pattern;
    }
    
    private int GetSelectedChordNo()
    {
        return Integer.valueOf(chordnos.getSelectionModel().getSelectedItem().toString());
    }
}
