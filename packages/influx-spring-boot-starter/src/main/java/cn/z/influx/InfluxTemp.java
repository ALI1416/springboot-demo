package cn.z.influx;

import cn.z.influx.autoconfigure.InfluxProperties;
import com.influxdb.client.*;
import com.influxdb.client.domain.*;
import com.influxdb.client.service.UsersService;
import com.influxdb.exceptions.InfluxException;
import com.influxdb.internal.AbstractRestClient;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import retrofit2.Call;
import retrofit2.Response;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * <h1>InfluxDB模板</h1>
 *
 * <p>
 * createDate 2024/09/03 11:35:59
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class InfluxTemp {

    private final InfluxDBClient influxClient;
    private final UsersService usersService;

    /**
     * 构造函数(自动注入)
     *
     * @param influxProperties InfluxProperties
     */
    public InfluxTemp(InfluxProperties influxProperties) throws Exception {
        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder()
                .protocols(Collections.singletonList(Protocol.HTTP_1_1))
                .readTimeout(influxProperties.getReadTimeout())
                .writeTimeout(influxProperties.getWriteTimeout())
                .connectTimeout(influxProperties.getConnectTimeout());
        InfluxDBClientOptions.Builder influxBuilder = InfluxDBClientOptions.builder()
                .url(influxProperties.getUrl())
                .bucket(influxProperties.getBucket())
                .org(influxProperties.getOrg())
                .okHttpClient(okHttpBuilder);
        if (influxProperties.getToken() != null && !influxProperties.getToken().isEmpty()) {
            influxBuilder.authenticateToken(influxProperties.getToken().toCharArray());
        } else if (influxProperties.getUsername() != null && !influxProperties.getUsername().isEmpty()
                && influxProperties.getPassword() != null && !influxProperties.getPassword().isEmpty()) {
            influxBuilder.authenticate(influxProperties.getUsername(), influxProperties.getPassword().toCharArray());
        }
        this.influxClient = InfluxDBClientFactory.create(influxBuilder.build()).setLogLevel(influxProperties.getLogLevel());
        // 反射获取UsersService类
        UsersApi usersApi = this.influxClient.getUsersApi();
        Class<? extends UsersApi> usersApiClass = usersApi.getClass();
        Field serviceField = usersApiClass.getDeclaredField("service");
        serviceField.setAccessible(true);
        this.usersService = (UsersService) serviceField.get(usersApi);
        serviceField.setAccessible(false);
    }

    /* ==================== 用户操作 ==================== */
    // region 用户操作

    /**
     * 获取所有用户
     *
     * @return List User
     */
    public List<User> userAll() {
        return influxClient.getUsersApi().findUsers();
    }

    /**
     * 获取当前用户
     *
     * @return User
     */
    public User user() {
        return influxClient.getUsersApi().me();
    }

    /**
     * 获取用户通过用户ID
     *
     * @param userId 用户ID
     * @return User
     */
    public User userGetById(String userId) {
        return influxClient.getUsersApi().findUserByID(userId);
    }

    /**
     * 获取用户通过用户名
     *
     * @param userName 用户名
     * @return User
     */
    public User userGetByName(String userName) {
        Call<Users> usersCall = usersService.getUsers(null, null, null, null, userName, null);
        Users users = execute(usersCall);
        return users.getUsers().get(0);
    }

    /**
     * 创建用户通过用户名
     *
     * @param userName 用户名
     * @return User
     */
    public User userCreateByName(String userName) {
        return influxClient.getUsersApi().createUser(userName);
    }

    /**
     * 更新用户
     *
     * @param user User
     * @return User
     */
    public User userUpdate(User user) {
        return influxClient.getUsersApi().updateUser(user);
    }

    /**
     * 更新当前用户密码
     *
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     */
    public void userUpdatePassword(String oldPassword, String newPassword) {
        influxClient.getUsersApi().meUpdatePassword(oldPassword, newPassword);
    }

    /**
     * 重置用户密码通过用户ID
     *
     * @param userId      用户ID
     * @param newPassword 新密码
     */
    public void userResetPasswordById(String userId, String newPassword) {
        influxClient.getUsersApi().updateUserPassword(userId, "", newPassword);
    }

    /**
     * 重置用户密码通过用户名
     *
     * @param userName    用户名
     * @param newPassword 新密码
     */
    public void userResetPasswordByName(String userName, String newPassword) {
        influxClient.getUsersApi().updateUserPassword(userGetByName(userName), "", newPassword);
    }

    /**
     * 删除用户通过用户ID
     *
     * @param userId 用户ID
     */
    public void userDeleteById(String userId) {
        influxClient.getUsersApi().deleteUser(userId);
    }

    /**
     * 删除用户通过用户名
     *
     * @param userName 用户名
     */
    public void userDeleteByName(String userName) {
        influxClient.getUsersApi().deleteUser(userGetByName(userName));
    }

    /**
     * 克隆用户通过用户ID
     *
     * @param userName    用户名
     * @param cloneUserId 被克隆的用户ID
     * @return User
     */
    public User userCloneById(String userName, String cloneUserId) {
        return influxClient.getUsersApi().cloneUser(userName, cloneUserId);
    }

    /**
     * 克隆用户通过用户名
     *
     * @param userName      用户名
     * @param cloneUserName 被克隆的用户名
     * @return User
     */
    public User userCloneByName(String userName, String cloneUserName) {
        return influxClient.getUsersApi().cloneUser(userName, userGetByName(cloneUserName).getId());
    }

    // endregion

    /* ==================== 组织基本操作 ==================== */
    // region 组织基本操作

    /**
     * 获取所有组织
     *
     * @return List Organization
     */
    public List<Organization> orgAll() {
        return influxClient.getOrganizationsApi().findOrganizations();
    }

    /**
     * 获取组织通过组织ID
     *
     * @param orgId 组织ID
     * @return Organization
     */
    public Organization orgGetById(String orgId) {
        return influxClient.getOrganizationsApi().findOrganizationByID(orgId);
    }

    /**
     * 获取组织通过组织名
     *
     * @param orgName 组织名
     * @return Organization
     */
    public Organization orgGetByName(String orgName) {
        OrganizationsQuery organizationsQuery = new OrganizationsQuery();
        organizationsQuery.setOrg(orgName);
        return influxClient.getOrganizationsApi().findOrganizations(organizationsQuery).get(0);
    }

    /**
     * 创建组织通过组织名
     *
     * @param orgName 组织名
     * @return Organization
     */
    public Organization orgCreateByName(String orgName) {
        return influxClient.getOrganizationsApi().createOrganization(orgName);
    }

    /**
     * 更新组织
     *
     * @param organization Organization
     * @return Organization
     */
    public Organization orgUpdate(Organization organization) {
        return influxClient.getOrganizationsApi().updateOrganization(organization);
    }

    /**
     * 删除组织通过组织ID
     *
     * @param orgId 组织ID
     */
    public void orgDeleteById(String orgId) {
        influxClient.getOrganizationsApi().deleteOrganization(orgId);
    }

    /**
     * 删除组织通过组织名
     *
     * @param orgName 组织名
     */
    public void orgDeleteByName(String orgName) {
        influxClient.getOrganizationsApi().deleteOrganization(orgGetByName(orgName));
    }

    /**
     * 克隆组织通过组织ID
     *
     * @param orgName    组织名
     * @param cloneOrgId 被克隆的组织ID
     * @return Organization
     */
    public Organization orgCloneById(String orgName, String cloneOrgId) {
        return influxClient.getOrganizationsApi().cloneOrganization(orgName, cloneOrgId);
    }

    /**
     * 克隆组织通过组织名
     *
     * @param orgName      组织名
     * @param cloneOrgName 被克隆的组织名
     * @return Organization
     */
    public Organization orgCloneByName(String orgName, String cloneOrgName) {
        return influxClient.getOrganizationsApi().cloneOrganization(orgName, orgGetByName(cloneOrgName));
    }

    // endregion

    /* ==================== 组织密钥操作 ==================== */
    // region 组织密钥操作

    /**
     * 获取组织密钥通过组织ID
     *
     * @param orgId 组织ID
     * @return SecretKeysResponse
     */
    public SecretKeysResponse orgSecretGetById(String orgId) {
        return influxClient.getOrganizationsApi().getSecrets(orgId);
    }

    /**
     * 获取组织密钥通过组织名
     *
     * @param orgName 组织名
     * @return SecretKeysResponse
     */
    public SecretKeysResponse orgSecretGetByName(String orgName) {
        return influxClient.getOrganizationsApi().getSecrets(orgGetByName(orgName));
    }

    /**
     * 添加组织密钥通过组织ID
     *
     * @param orgId   组织ID
     * @param secrets 密钥Map
     */
    public void orgSecretAddById(String orgId, Map<String, String> secrets) {
        influxClient.getOrganizationsApi().putSecrets(secrets, orgId);
    }

    /**
     * 添加组织密钥通过组织名
     *
     * @param orgName 组织名
     * @param secrets 密钥Map
     */
    public void orgSecretAddByName(String orgName, Map<String, String> secrets) {
        influxClient.getOrganizationsApi().putSecrets(secrets, orgGetByName(orgName));
    }

    /**
     * 删除组织密钥通过组织ID
     *
     * @param orgId   组织ID
     * @param secrets 密钥key列表
     */
    public void orgSecretDeleteById(String orgId, List<String> secrets) {
        influxClient.getOrganizationsApi().deleteSecrets(secrets, orgId);
    }

    /**
     * 删除组织密钥通过组织名
     *
     * @param orgName 组织名
     * @param secrets 密钥key列表
     */
    public void orgSecretDeleteByName(String orgName, List<String> secrets) {
        influxClient.getOrganizationsApi().deleteSecrets(secrets, orgGetByName(orgName));
    }

    // endregion

    /* ==================== 组织成员操作 ==================== */
    // region 组织成员操作

    /**
     * 通过组织ID获取成员
     *
     * @param orgId 组织ID
     * @return List ResourceMember
     */
    public List<ResourceMember> orgMemberGetById(String orgId) {
        return influxClient.getOrganizationsApi().getMembers(orgId);
    }

    /**
     * 通过组织名获取成员
     *
     * @param orgName 组织名
     * @return List ResourceMember
     */
    public List<ResourceMember> orgMemberGetByName(String orgName) {
        return influxClient.getOrganizationsApi().getMembers(orgGetByName(orgName));
    }

    /**
     * 通过组织ID添加成员ID
     *
     * @param orgId    组织ID
     * @param memberId 成员ID
     * @return ResourceMember
     */
    public ResourceMember orgMemberAddById(String orgId, String memberId) {
        return influxClient.getOrganizationsApi().addMember(memberId, orgId);
    }

    /**
     * 通过组织名添加成员名
     *
     * @param orgName    组织名
     * @param memberName 成员名
     * @return ResourceMember
     */
    public ResourceMember orgMemberAddByName(String orgName, String memberName) {
        return influxClient.getOrganizationsApi().addMember(userGetByName(memberName), orgGetByName(orgName));
    }

    /**
     * 通过组织ID删除成员ID
     *
     * @param orgId    组织ID
     * @param memberId 成员ID
     */
    public void orgMemberDeleteById(String orgId, String memberId) {
        influxClient.getOrganizationsApi().deleteMember(memberId, orgId);
    }

    /**
     * 通过组织名删除成员名
     *
     * @param orgName    组织名
     * @param memberName 成员名
     */
    public void orgMemberDeleteByName(String orgName, String memberName) {
        influxClient.getOrganizationsApi().deleteMember(userGetByName(memberName), orgGetByName(orgName));
    }

    // endregion

    /* ==================== 组织所有者操作 ==================== */
    // region 组织所有者操作

    /**
     * 通过组织ID获取所有者
     *
     * @param orgId 组织ID
     * @return List ResourceOwner
     */
    public List<ResourceOwner> orgOwnerGetById(String orgId) {
        return influxClient.getOrganizationsApi().getOwners(orgId);
    }

    /**
     * 通过组织名获取所有者
     *
     * @param orgName 组织名
     * @return List ResourceOwner
     */
    public List<ResourceOwner> orgOwnerGetByName(String orgName) {
        return influxClient.getOrganizationsApi().getOwners(orgGetByName(orgName));
    }

    /**
     * 通过组织ID添加所有者ID
     *
     * @param orgId   组织ID
     * @param ownerId 所有者ID
     * @return ResourceOwner
     */
    public ResourceOwner orgOwnerAddById(String orgId, String ownerId) {
        return influxClient.getOrganizationsApi().addOwner(ownerId, orgId);
    }

    /**
     * 通过组织名添加所有者名
     *
     * @param orgName   组织名
     * @param ownerName 所有者名
     * @return ResourceOwner
     */
    public ResourceOwner orgOwnerAddByName(String orgName, String ownerName) {
        return influxClient.getOrganizationsApi().addOwner(userGetByName(ownerName), orgGetByName(orgName));
    }

    /**
     * 通过组织ID删除所有者ID
     *
     * @param orgId   组织ID
     * @param ownerId 所有者ID
     */
    public void orgOwnerDeleteById(String orgId, String ownerId) {
        influxClient.getOrganizationsApi().deleteOwner(ownerId, orgId);
    }

    /**
     * 通过组织名删除所有者名
     *
     * @param orgName   组织名
     * @param ownerName 所有者名
     */
    public void orgOwnerDeleteByName(String orgName, String ownerName) {
        influxClient.getOrganizationsApi().deleteOwner(userGetByName(ownerName), orgGetByName(orgName));
    }

    // endregion

    /* ==================== 标签操作 ==================== */
    // region 标签操作

    // endregion

    /* ==================== 储存桶基本操作 ==================== */
    // region 储存桶基本操作

    /**
     * 获取所有储存桶
     *
     * @return List Bucket
     */
    public List<Bucket> bucketAll() {
        return influxClient.getBucketsApi().findBuckets();
    }

    /**
     * 获取储存桶通过组织ID
     *
     * @param orgId 组织ID
     * @return List Bucket
     */
    public List<Bucket> bucketGetByOrgId(String orgId) {
        BucketsQuery bucketsQuery = new BucketsQuery();
        bucketsQuery.setOrgID(orgId);
        return influxClient.getBucketsApi().findBuckets(bucketsQuery);
    }

    /**
     * 获取储存桶通过组织名
     *
     * @param orgName 组织名
     * @return List Bucket
     */
    public List<Bucket> bucketGetByOrgName(String orgName) {
        return influxClient.getBucketsApi().findBucketsByOrgName(orgName);
    }

    /**
     * 获取储存桶通过储存桶ID
     *
     * @param bucketId 储存桶ID
     * @return Bucket
     */
    public Bucket bucketGetById(String bucketId) {
        return influxClient.getBucketsApi().findBucketByID(bucketId);
    }

    /**
     * 获取储存桶通过储存桶名
     *
     * @param orgName    组织名
     * @param bucketName 储存桶名
     * @return Bucket
     */
    public Bucket bucketGetByName(String orgName, String bucketName) {
        BucketsQuery bucketsQuery = new BucketsQuery();
        bucketsQuery.setOrg(orgName);
        bucketsQuery.setName(bucketName);
        return influxClient.getBucketsApi().findBuckets(bucketsQuery).get(0);
    }

    /**
     * 创建储存桶通过组织ID
     *
     * @param orgId      组织ID
     * @param bucketName 储存桶名
     * @return Bucket
     */
    public Bucket bucketCreateByOrgId(String orgId, String bucketName) {
        return influxClient.getBucketsApi().createBucket(bucketName, orgId);
    }

    /**
     * 创建储存桶通过组织名
     *
     * @param orgName    组织名
     * @param bucketName 储存桶名
     * @return Bucket
     */
    public Bucket bucketCreateByOrgName(String orgName, String bucketName) {
        return influxClient.getBucketsApi().createBucket(bucketName, orgGetByName(orgName));
    }

    /**
     * 更新储存桶
     *
     * @param bucket 储存桶
     * @return Bucket
     */
    public Bucket bucketUpdate(Bucket bucket) {
        return influxClient.getBucketsApi().updateBucket(bucket);
    }

    /**
     * 删除储存桶通过储存桶ID
     *
     * @param bucketId 储存桶ID
     */
    public void bucketDeleteById(String bucketId) {
        influxClient.getBucketsApi().deleteBucket(bucketId);
    }

    /**
     * 删除储存桶通过储存桶名
     *
     * @param orgName    组织名
     * @param bucketName 储存桶名
     */
    public void bucketDeleteByName(String orgName, String bucketName) {
        influxClient.getBucketsApi().deleteBucket(bucketGetByName(orgName, bucketName));
    }

    /**
     * 克隆储存桶通过储存桶ID
     *
     * @param bucketName    储存桶名
     * @param cloneBucketId 被克隆的储存桶ID
     * @return Bucket
     */
    public Bucket bucketCloneById(String bucketName, String cloneBucketId) {
        return influxClient.getBucketsApi().cloneBucket(bucketName, cloneBucketId);
    }

    /**
     * 克隆储存桶通过储存桶名
     *
     * @param orgName         组织名
     * @param bucketName      储存桶名
     * @param cloneBucketName 被克隆的储存桶名
     * @return Bucket
     */
    public Bucket bucketCloneByName(String orgName, String bucketName, String cloneBucketName) {
        return influxClient.getBucketsApi().cloneBucket(bucketName, bucketGetByName(orgName, cloneBucketName));
    }

    // endregion

    /* ==================== 储存桶成员操作 ==================== */
    // region 储存桶成员操作

    // endregion

    /* ==================== 储存桶所有者操作 ==================== */
    // region 储存桶所有者操作

    // endregion

    /* ==================== 储存桶标签操作 ==================== */
    // region 储存桶标签操作

    // endregion


    /* ==================== 私有方法 ==================== */
    // region 私有方法

    /**
     * 执行
     *
     * @param call Call
     * @param <T>  T
     * @return T
     * @see AbstractRestClient#execute(Call)
     */
    private <T> T execute(Call<T> call) {
        try {
            Response<T> response = call.execute();
            if (response.isSuccessful()) {
                return response.body();
            } else {
                throw new InfluxException(response);
            }
        } catch (Exception e) {
            throw new InfluxException(e);
        }
    }

    // endregion

}
