package com.aionemu.gameserver.model.instance.instancereward;

public class ShugoEmperorVaultReward extends InstanceReward
{
  private int points;
  private int rank = 7;
  private int keys = 0;
  
  public ShugoEmperorVaultReward(Integer mapId, int instanceId) {
    super(mapId, instanceId);
  }
  
  public void addPoints(int points) {
	this.points += points;
  }
  
  public int getPoints() {
    return points;
  }
  
  public void addKeys(int keys) {
    this.keys += keys;
  }
  
  public int getKeys() {
    return keys;
  }
  
  public void setRank(int rank) {
    this.rank = rank;
  }
  
  public int getRank(){
    return rank;
  }
}
