package com.demo.tool.entity.elasticsearch.search;


import com.demo.tool.entity.elasticsearch.search.ShardProfile;

import java.util.List;

/**
 * <h1>配置</h1>
 *
 * <p>
 * createDate 2023/09/12 15:29:02
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class Profile {

    /**
     * 分片配置
     */
    private List<ShardProfile> shards;

    public Profile() {
    }

    public Profile(co.elastic.clients.elasticsearch.core.search.Profile profile) {
        this.shards = ShardProfile.toList(profile.shards());
    }

    public List<ShardProfile> getShards() {
        return shards;
    }

    public void setShards(List<ShardProfile> shards) {
        this.shards = shards;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "shards=" + shards +
                '}';
    }

}
