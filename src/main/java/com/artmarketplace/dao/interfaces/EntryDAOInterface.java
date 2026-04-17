
package com.ingcollegeapt.week7twebapp.dao.interfaces;

import com.ingcollegeapt.week7twebapp.model.Entry;
import java.util.ArrayList;

public interface EntryDAOInterface {
   //Enter learning entry (TODO): Fetch all entries for a topic
    ArrayList<Entry> fetchAllEntries(int topicId);
    
    //Enter learning entry (TODO): Insert entry into database
    boolean insertEntry(Entry entry, int topicId);

    //Update the existing Entry
    boolean updateEntry(Entry newEntry, int topicId);
    
    //Delete the existing Entry of the topic
    boolean deleteEntry(int topicId); 
}
