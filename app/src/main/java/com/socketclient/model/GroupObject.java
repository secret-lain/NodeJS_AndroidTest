package com.socketclient.model;

import java.util.List;

/**
 * Created by admin on 2017-05-12.
 */

public class GroupObject {
    public int x; // 좌표. 해당 위치의 정보는 MapObject를 검색할 때 사용되기도 한다.
    public int y;

    /*
    * 보유자원들..(그룹별로 들고있을 수 있는 자원이 다르다)
    * 그룹정보들..(적재가능량, 유닛수, 정찰력, 이동속도, 소비식량 등등..)
    * 정보는 클라이언트가 직접 계산하는게 나을것같음
    * */

    public String userName; // 실제로 사용될 것 같진 않다
    public int gid;
    public List<UnitObject> units;
}
