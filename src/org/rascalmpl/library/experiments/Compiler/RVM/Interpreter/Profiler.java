package org.rascalmpl.library.experiments.Compiler.RVM.Interpreter;

import java.io.PrintWriter;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;

import org.eclipse.imp.pdb.facts.IList;
import org.eclipse.imp.pdb.facts.IListWriter;
import org.eclipse.imp.pdb.facts.IMap;
import org.eclipse.imp.pdb.facts.IMapWriter;
import org.eclipse.imp.pdb.facts.ISourceLocation;
import org.eclipse.imp.pdb.facts.IValueFactory;
import org.eclipse.imp.pdb.facts.type.Type;
import org.eclipse.imp.pdb.facts.type.TypeFactory;
import org.rascalmpl.values.ValueFactoryFactory;

class Count {
	int ticks;
	
	Count(){
		ticks = 1;
	}
	
	public void increment(){
		ticks += 1;
	}
	
	public int getTicks(){
		return ticks;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ticks;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Count other = (Count) obj;
		if (ticks != other.ticks)
			return false;
		return true;
	}
	
}

public class Profiler extends Thread {
	private ProfilingLocationCollector reporter;
	private HashMap<ISourceLocation,Count> data;
	private volatile boolean running;
	private long resolution = 1;
	
	public Profiler(ProfilingLocationCollector reporter){
		this.reporter = reporter;
		this.data = new HashMap<ISourceLocation,Count>();
		running = true;
	}
	
	@Override
	public void run(){
		while(running){
			ISourceLocation stat = reporter.getLocation();
			if(stat != null){
				Count currentCount = data.get(stat);
				if(currentCount == null)
					data.put(stat, new Count());
				else
					currentCount.increment();
			}
			try {
				sleep(resolution);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void pleaseStop(){
		running = false;
	}
	
	/* Extract a list of entries from the collected data and
	 * sort it with descending tick values.
	 */
	
	private List<Map.Entry<ISourceLocation, Count>> sortData(){
	  List<Map.Entry<ISourceLocation, Count>> sortedData = new Vector<Entry<ISourceLocation, Count>>(data.entrySet());

	  java.util.Collections.sort(sortedData, new Comparator<Map.Entry<ISourceLocation, Count>>(){
	    public int compare(Entry<ISourceLocation, Count> entry1, Entry<ISourceLocation, Count> entry2) {
	      return ((entry1.getValue().getTicks() == entry2.getValue().getTicks()) ? 0 : 
	        (entry1.getValue().getTicks() < entry2.getValue().getTicks() ? 1 : -1));
	    }
	  });
	  return sortedData;
	}
	
	public IList getProfileData(){
		TypeFactory TF = TypeFactory.getInstance();
		Type elemType = TF.tupleType(TF.sourceLocationType(), TF.integerType());
		IValueFactory VF = ValueFactoryFactory.getValueFactory();
		IListWriter w = VF.listWriter(elemType);
		for(Map.Entry<ISourceLocation, Count> e : sortData()){
			w.insert(VF.tuple(e.getKey(), VF.integer(e.getValue().getTicks())));
		}
		return w.done();
	}
	
	public IMap report(){
		IValueFactory VF = ValueFactoryFactory.getValueFactory();
		IMapWriter w = VF.mapWriter();
		for(Map.Entry<ISourceLocation, Count> e : sortData()){
			w.insert(e.getKey(), VF.integer(e.getValue().getTicks()));
		}
		return w.done();
	}
	
	public void report(PrintWriter out) {
	  List<Map.Entry<ISourceLocation, Count>> sortedData = sortData();

	  int maxURL = 1;
	  long nTicks = 0;

	  for(Map.Entry<ISourceLocation, Count> e : sortedData){
	    int sz = e.getKey().getURI().toString().length();
	    if(sz > maxURL)
	      maxURL = sz;
	    nTicks += e.getValue().getTicks();
	  }

	  if(nTicks > 0){
		  out.printf("PROFILE: %d data points, %d ticks, tick = %d milliSecs\n", data.size(), nTicks, resolution);
		  out.printf("%8s%9s  %s\n", "Ticks", "%", "Source");

		  for(Map.Entry<ISourceLocation, Count> e : sortedData){

			  int ticks = e.getValue().getTicks();
			  double perc = (ticks * 100.0)/nTicks;

			  String source = String.format("%s", e.getKey().toString());

			  out.printf("%8d%8.1f%%  %s\n", ticks, perc, source);
		  }

		
	  } else {
		  out.printf("PROFILE: not enough data collected\n");
	  }
	  // Make sure that our output is seen:
	  out.flush();
	}

}
