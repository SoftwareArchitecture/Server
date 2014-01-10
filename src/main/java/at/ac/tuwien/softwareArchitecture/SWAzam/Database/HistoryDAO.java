package at.ac.tuwien.softwareArchitecture.SWAzam.Database;

import java.util.List;

import at.ac.tuwien.softwareArchitecture.SWAzam.model.History;

public interface HistoryDAO {
	   public History save(History history);
	   public boolean update(History history);
	   public History findByHistoryNumber(int historyid);
	   public boolean delete(History history);
	   public List<History> getHistories();
	   public History searchWithSession(String Sessionkey);
}
