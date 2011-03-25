module util::Monitor

@doc{
 Register a job with a name, a default amount of work contributed to the overall task,
 and an unknown amount of steps to do.
}
@javaClass{org.rascalmpl.library.util.Monitor}
@reflect
public void java startJob(str name);

@doc{
  Register a job with a name and a total amount of steps to do (this will also be the amount
  of work contributed to the parent job, if any).
}
@javaClass{org.rascalmpl.library.util.Monitor}
@reflect 
public void java startJob(str name, int totalWork);

@doc{
  Register a job with a name, the amount this will contribute to the overall task,
  and a total amount of steps to do.
}
@javaClass{org.rascalmpl.library.util.Monitor}
@reflect 
public void java startJob(str name, int workShare, int totalWork);

@doc{
 Log the <bold>start</bold> of an event. 
}
@javaClass{org.rascalmpl.library.util.Monitor}
@reflect 
public void java event(str name);

@doc{
  Log the start of an event with the amount of work that will be done when it's finished.
  An event is finished when the next event is logged, or when endJob() is called.
}
@javaClass{org.rascalmpl.library.util.Monitor}
@reflect 
public void java event(str name, int inc);

@doc{
  Log the start of an event with the amount of work that will be done when it's finished.
  An event is finished when the next event is logged, or when endJob() is called.
}
@javaClass{org.rascalmpl.library.util.Monitor}
@reflect 
public void java event(int inc);

@doc{
  This should always be called once for every startJob, unless an exception is thrown.
  @return The amount of work completed for this job (to help in future estimates)
}
@javaClass{org.rascalmpl.library.util.Monitor}
@reflect 
public int java endJob(bool succeeded);

@doc{
  Set the estimated remaining work for the current (sub)job. 
  @param work Amount of work remaining to be done, or 0 for unknown.
}
@javaClass{org.rascalmpl.library.util.Monitor}
@reflect 
public void java todo(int work); 