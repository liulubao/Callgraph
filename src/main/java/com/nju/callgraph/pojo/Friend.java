package com.nju.callgraph.pojo;

public class Friend {
    private Integer id;

    private String followingloginname;

    private String followingviewname;

    private String followedloginname;

    private String followedviewname;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFollowingloginname() {
        return followingloginname;
    }

    public void setFollowingloginname(String followingloginname) {
        this.followingloginname = followingloginname == null ? null : followingloginname.trim();
    }

    public String getFollowingviewname() {
        return followingviewname;
    }

    public void setFollowingviewname(String followingviewname) {
        this.followingviewname = followingviewname == null ? null : followingviewname.trim();
    }

    public String getFollowedloginname() {
        return followedloginname;
    }

    public void setFollowedloginname(String followedloginname) {
        this.followedloginname = followedloginname == null ? null : followedloginname.trim();
    }

    public String getFollowedviewname() {
        return followedviewname;
    }

    public void setFollowedviewname(String followedviewname) {
        this.followedviewname = followedviewname == null ? null : followedviewname.trim();
    }
}