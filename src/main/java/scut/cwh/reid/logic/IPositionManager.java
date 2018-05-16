package scut.cwh.reid.logic;

import scut.cwh.reid.domain.Position;
import scut.cwh.reid.domain.WifiInfo;

import java.util.Set;

public interface IPositionManager {
    public Position calculationPosition(Set<WifiInfo> wifiInfoSetPre);
}
