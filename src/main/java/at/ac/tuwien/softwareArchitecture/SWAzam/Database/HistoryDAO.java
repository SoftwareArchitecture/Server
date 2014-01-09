package at.ac.tuwien.softwareArchitecture.SWAzam.Database;

import java.util.List;

import at.ac.tuwien.softwareArchitecture.SWAzam.model.History;

public interface HistoryDAO {
	   public boolean save(History history);
	   public boolean update(History history);
	   public History findByAccountNumber(int historyid);
	   public boolean delete(History history);
	   public List<History> getHistories();
}
