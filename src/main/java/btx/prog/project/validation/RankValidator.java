package btx.prog.project.validation;

import btx.prog.project.domain.Pilot;
import btx.prog.project.domain.Pilot.Rank;

public class RankValidator extends EnumValidator<Rank> {

	public RankValidator() {
		super(Pilot.Rank.values());
	}

}
