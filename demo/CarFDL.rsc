module CarFDL

import Set;

alias feature = str;

public set[feature] Transmission    = { "automatic", "manual" };

public set[set[feature]] Engine     = power({"electric", "gasoline"}) - {{}};

set[feature] HorsePower      = {"lowPower", "mediumPower", "highPower"};

set[feature] PullsTrailerOpt = {"pullsTrailer", "pullsNoTrailer"};

set[set[feature]] CarFeatures = { {T} + E + {H} + {PT} | 
			feature T : Transmission, 
			set[feature] E : Engine, 
			feature H : HorsePower, 
			feature PT : PullsTrailerOpt, 
			(PT == "pullsTrailer") ==> (H == "highPower")
};

public bool test(){
	return 	size(CarFeatures) == 24;
}

