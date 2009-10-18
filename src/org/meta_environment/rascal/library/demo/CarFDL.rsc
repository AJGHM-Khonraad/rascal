module demo::CarFDL

import Set;

// Feature Description Language (FDL) is a formalism to describe the features
// of a system, see
// A. van Deursen and P. Klint, 
// Domain-Specific Language Design Requires Feature Descriptions, 
// Journal of Computing and Information Technology, 10 (1):1-18, March 2002.

// Here we describe an example from that paper that describes the features of cars.
// We model features directly in Rascal


// A feature is just a string

alias feature = str;

// The transmission feature is described by an or of features:
// it is either "manual" or "automatic"

public set[feature] Transmission    = { "automatic", "manual" };

// The Engine feature is descibed by a more-of of features:
// any subset of {"electric", "gasoline"} is possible.

public set[set[feature]] Engine     = power({"electric", "gasoline"}) - {{}};

set[feature] HorsePower      = {"lowPower", "mediumPower", "highPower"};

set[feature] PullsTrailerOpt = {"pullsTrailer", "pullsNoTrailer"};

// Calculate all possible feature sets

set[set[feature]] CarFeatures = { {{T} + E + {H} + {PT}} | 
			feature T <- Transmission, 
			set[feature] E <- Engine, 
			feature H <- HorsePower, 
			feature PT <- PullsTrailerOpt, 
			(PT == "pullsTrailer") ==> (H == "highPower")
};

// Tests

test size(CarFeatures) == 24;

