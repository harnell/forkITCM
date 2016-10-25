package clueGame;

public class Solution {
	
	public String person;
	public String room;
	public String weapon;
	public Solution(String person, String room, String weapon) {
		super();
		this.person = person;
		this.room = room;
		this.weapon = weapon;
	}
	
	public boolean equals(Solution sol){
		if (sol.person.equals(this.person) && sol.room.equals(this.room) && sol.weapon.equals(this.weapon)){
			return true;
		}
		else{
			return false;
		}
	}

	@Override
	public String toString() {
		return "Solution [person=" + person + ", room=" + room + ", weapon=" + weapon + "]";
	}
	
}
