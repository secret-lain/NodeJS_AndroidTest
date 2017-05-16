package com.socketclient.model;

/**
 * Created by admin on 2017-05-15.
 */

import java.util.List;

/**
 * type{
 *     0 = 개간됨, 성 혹은 거점
 *     1 = 초원,
 *     2 = 수풀지대,
 *     3 = 물가 ( 일반 방법으로 진입 불가),
 *     4 = 황야
 *     5 = 마나폭풍지대 ( 일반 방법으로 진입 위험, 진입 시 일반 캐릭터 사망 )
 * }
 */
public class MapObject {
    public int x;
    public int y;
    public int type;
    public String owner; // 닉네임? 아이디?
    public List<BuildingObject> buildings;
}
