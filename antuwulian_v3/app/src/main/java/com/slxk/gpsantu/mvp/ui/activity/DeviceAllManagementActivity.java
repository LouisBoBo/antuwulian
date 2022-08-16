package com.slxk.gpsantu.mvp.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.loadmore.LoadMoreView;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.slxk.gpsantu.app.MyApplication;
import com.slxk.gpsantu.di.component.DaggerDeviceAllManagementComponent;
import com.slxk.gpsantu.mvp.contract.DeviceAllManagementContract;
import com.slxk.gpsantu.mvp.model.api.ModuleValueService;
import com.slxk.gpsantu.mvp.model.bean.AlertBean;
import com.slxk.gpsantu.mvp.model.bean.DeviceBaseResultBean;
import com.slxk.gpsantu.mvp.model.bean.DeviceGroupResultBean;
import com.slxk.gpsantu.mvp.model.bean.GroupAddResultBean;
import com.slxk.gpsantu.mvp.model.bean.TaskProgressResultBean;
import com.slxk.gpsantu.mvp.model.entity.BaseBean;
import com.slxk.gpsantu.mvp.model.putbean.DeviceGroupPutBean;
import com.slxk.gpsantu.mvp.model.putbean.FreezeEquipmentPutBean;
import com.slxk.gpsantu.mvp.model.putbean.GroupAddPutBean;
import com.slxk.gpsantu.mvp.model.putbean.GroupDeletePutBean;
import com.slxk.gpsantu.mvp.model.putbean.GroupEditPutBean;
import com.slxk.gpsantu.mvp.model.putbean.RemoveDevicePutBean;
import com.slxk.gpsantu.mvp.model.putbean.TaskProgressPubBean;
import com.slxk.gpsantu.mvp.model.putbean.TransferDevicePutBean;
import com.slxk.gpsantu.mvp.presenter.DeviceAllManagementPresenter;

import com.slxk.gpsantu.R;
import com.slxk.gpsantu.mvp.ui.adapter.GroupListManageAdapter;
import com.slxk.gpsantu.mvp.ui.adapter.GroupManagementAdapter;
import com.slxk.gpsantu.mvp.ui.view.MyLoadMoreView;
import com.slxk.gpsantu.mvp.utils.ConstantValue;
import com.slxk.gpsantu.mvp.utils.ResultDataUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.slxk.gpsantu.mvp.utils.Utils;
import com.slxk.gpsantu.mvp.weiget.AlertAppDialog;
import com.slxk.gpsantu.mvp.weiget.DeviceFailDialog;
import com.slxk.gpsantu.mvp.weiget.GroupAddDialog;
import com.slxk.gpsantu.mvp.weiget.GroupEditDialog;
import com.slxk.gpsantu.mvp.weiget.GroupManagePopupwindow;
import com.slxk.gpsantu.mvp.weiget.GroupTransferDeviceDialog;
import com.slxk.gpsantu.mvp.weiget.TaskProgressDialog;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:分组管理首页
 * <p>
 * Created by MVPArmsTemplate on 09/16/2021 14:58
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class DeviceAllManagementActivity extends BaseActivity<DeviceAllManagementPresenter> implements DeviceAllManagementContract.View {

    @BindView(R.id.toolbar_right)
    TextView tvRight;
    @BindView(R.id.view)
    View view;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.srl_view)
    SwipeRefreshLayout srlView;

    private TextView tvPrompt;
    private GroupManagementAdapter mAdapter;
    private ArrayList<DeviceGroupResultBean.GaragesBean> groupBeans; // 设备分组
    private String mLastGroupId; // 最后获取到的gid，没有可以为空
    private int mLimitSize = 100; // 限制分组获取数量
    private String mSid; // 分组id
    private String mGroupName; // 分组名称

    private GroupManagePopupwindow mPopupWindow; // 菜单栏
    private final static int Intent_Add_Device = 10; // 添加设备
    private final static int Intent_Device_Manager = 11; // 设备管理

    private static final int mCountDownTime = 2; // 倒计时固定时长
    private int mCountDown = mCountDownTime; // 倒计时递减时长
    private String taskId; // 任务id
    private TaskProgressDialog taskProgressDialog;

    private String familyAuth; // 用户权限

    public static Intent newInstance() {
        return new Intent(MyApplication.getMyApp(), DeviceAllManagementActivity.class);
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerDeviceAllManagementComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_device_all_management; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle(getString(R.string.txt_group_manage));
        familyAuth = ConstantValue.getFamilyAuth();
        tvRight.setText(getString(R.string.txt_operation));
        groupBeans = new ArrayList<>();

        if (!TextUtils.isEmpty(familyAuth)) {
            if (familyAuth.contains(ResultDataUtils.Family_Auth_0) || familyAuth.contains(ResultDataUtils.Family_Auth_1)
                    || familyAuth.contains(ResultDataUtils.Family_Auth_10) || familyAuth.contains(ResultDataUtils.Family_Auth_13)
                    || familyAuth.contains(ResultDataUtils.Family_Auth_11) || familyAuth.contains(ResultDataUtils.Family_Auth_2)) {
                tvRight.setVisibility(View.VISIBLE);
            }
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        srlView.setColorSchemeResources(R.color.color_2E7BEC, R.color.color_2E7BEC);
        srlView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSid = "";
                mLastGroupId = "";
                mGroupName = "";
                getDeviceGroupList(false, true);
            }
        });

        View statusView = LayoutInflater.from(this).inflate(R.layout.layout_status, recyclerView, false);
        tvPrompt = statusView.findViewById(R.id.txt_wrong_status);
        tvPrompt.setText(getString(R.string.txt_group_no_data));
        tvPrompt.setVisibility(View.GONE);

        mAdapter = new GroupManagementAdapter(R.layout.item_group_manage_all, groupBeans);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setEmptyView(statusView);

        LoadMoreView loadMoreView = new MyLoadMoreView();
        mAdapter.setLoadMoreView(loadMoreView);
        mAdapter.setEnableLoadMore(true);
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getDeviceGroupList(false, false);
            }
        }, recyclerView);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                onDeviceManage(position);
            }
        });
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (position < groupBeans.size()) {
                    for (DeviceGroupResultBean.GaragesBean bean : groupBeans) {
                        bean.setSelect(false);
                    }
                    groupBeans.get(position).setSelect(true);
                    mAdapter.notifyDataSetChanged();
                    mSid = groupBeans.get(position).getSid();
                    mGroupName = groupBeans.get(position).getSname();
                }
            }
        });

        getDeviceGroupList(true, false);
    }

    /**
     * 显示无数据提示
     */
    private void onShowNoData() {
        if (groupBeans.size() > 0) {
            tvPrompt.setVisibility(View.GONE);
        } else {
            tvPrompt.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 获取数据
     */
    private void getData() {
        mSid = "";
        mLastGroupId = "";
        mGroupName = "";
        getDeviceGroupList(false, true);
    }

    /**
     * 获取车组织列表和车组列表
     *
     * @param isShow    是否显示加载框
     * @param isRefresh 是否是刷新设备分组
     */
    private void getDeviceGroupList(boolean isShow, boolean isRefresh) {
        if (ConstantValue.isAccountLogin()) {
            DeviceGroupPutBean.ParamsBean paramsBean = new DeviceGroupPutBean.ParamsBean();
            paramsBean.setF_limit_size(0);
            paramsBean.setG_limit_size(mLimitSize);
            paramsBean.setFamilyid(ConstantValue.getFamilySid());
            if (!TextUtils.isEmpty(mLastGroupId)) {
                paramsBean.setLast_gid(mLastGroupId);
            }
            DeviceGroupPutBean bean = new DeviceGroupPutBean();
            bean.setParams(paramsBean);
            bean.setFunc(ModuleValueService.Fuc_For_Device_Group_List);
            bean.setModule(ModuleValueService.Module_For_Device_Group_List);

            if (getPresenter() != null) {
                getPresenter().getDeviceGroupList(bean, isShow, isRefresh);
            }
        }
    }

    /**
     * 跳转设备管理
     */
    private void onDeviceManage(int position) {
        if (position < groupBeans.size()) {
            Intent intent = new Intent(this, DeviceManagementActivity.class);
            intent.putExtra("sid", groupBeans.get(position).getSid());
            intent.putExtra("name", groupBeans.get(position).getSname());
            startActivityForResult(intent, Intent_Device_Manager);
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }

    @OnClick(R.id.toolbar_right)
    public void onViewClicked() {
        if (Utils.isButtonQuickClick(System.currentTimeMillis())) {
            if (mPopupWindow != null && mPopupWindow.isShowing()) {
                mPopupWindow.dismiss();
            } else {
                mPopupWindow = new GroupManagePopupwindow(this, 0);
                mPopupWindow.setManageMenuChange(new GroupManagePopupwindow.onManageMenuChange() {
                    @Override
                    public void onMenuSelect(int id) {
                        // 0:转移，7：删除设备，2：删除分组，3：编辑分组，4：添加分组，5：添加设备，6：冻结设备
                        switch (id) {
                            case 0:
                                onTransferDevice();
                                break;
                            case 7:
                                onDeleteDevice(0);
                                break;
                            case 2:
                                onDeleteDevice(1);
                                break;
                            case 3:
                                editGroupName();
                                break;
                            case 4:
                                addNewGroup();
                                break;
                            case 5:
                                addNewDevice();
                                break;
                            case 6:
                                onFreezeEquipment();
                                break;
                        }
                        mPopupWindow.dismiss();
                    }
                });
                mPopupWindow.showAsDropDown(view);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == Intent_Add_Device || requestCode == Intent_Device_Manager) {
                mSid = "";
                mLastGroupId = "";
                mGroupName = "";
                getDeviceGroupList(true, true);
                setResult(Activity.RESULT_OK);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 是否选择了分组
     *
     * @return
     */
    private boolean isGroupSelect() {
        if (TextUtils.isEmpty(mSid)) {
            ToastUtils.showShort(getString(R.string.txt_group_select_hint));
            return false;
        } else {
            return true;
        }
    }

    /**
     * 转移分组
     */
    private void onTransferDevice() {
        if (!isGroupSelect()) {
            return;
        }
        List<DeviceGroupResultBean.GaragesBean> groupBeansAll = new ArrayList<>(); // 设备分组总数
        groupBeansAll.addAll(groupBeans);
        GroupTransferDeviceDialog dialog = new GroupTransferDeviceDialog();
        dialog.show(getSupportFragmentManager(), mSid, groupBeansAll, new GroupTransferDeviceDialog.onGroupTransferDeviceChange() {
            @Override
            public void onGroupTransferDevice(String sid) {
                submitTransferDevice(sid);
            }
        });
    }

    /**
     * 转移设备
     *
     * @param sid
     */
    private void submitTransferDevice(String sid) {
        TransferDevicePutBean.ParamsBean paramsBean = new TransferDevicePutBean.ParamsBean();
        paramsBean.setSgid(mSid);
        paramsBean.setSgid_new(sid);

        TransferDevicePutBean bean = new TransferDevicePutBean();
        bean.setParams(paramsBean);
        bean.setFunc(ModuleValueService.Fuc_For_Group_Transfer_Device);
        bean.setModule(ModuleValueService.Module_For_Group_Transfer_Device);

        if (getPresenter() != null) {
            getPresenter().submitTransferDevice(bean);
        }
    }

    /**
     * 冻结设备
     */
    private void onFreezeEquipment() {
        if (!isGroupSelect()) {
            return;
        }

        AlertBean bean = new AlertBean();
        bean.setTitle(getString(R.string.txt_tip));
        bean.setAlert(getString(R.string.txt_freeze_equipment_group));
        bean.setType(0);
        AlertAppDialog dialog = new AlertAppDialog();
        dialog.show(getSupportFragmentManager(), bean, new AlertAppDialog.onAlertDialogChange() {
            @Override
            public void onConfirm() {
                submitFreezeEquipment();
            }

            @Override
            public void onCancel() {

            }
        });
    }

    /**
     * 提交冻结设备
     */
    private void submitFreezeEquipment() {
        FreezeEquipmentPutBean.ParamsBean paramsBean = new FreezeEquipmentPutBean.ParamsBean();
        paramsBean.setSgid(mSid);

        FreezeEquipmentPutBean bean = new FreezeEquipmentPutBean();
        bean.setParams(paramsBean);
        bean.setFunc(ModuleValueService.Fuc_For_Freeze_Equipment);
        bean.setModule(ModuleValueService.Module_For_Freeze_Equipment);

        if (getPresenter() != null) {
            getPresenter().submitFreezeEquipment(bean);
        }
    }

    /**
     * 删除分组
     *
     * @param type 删除类型，0：删除设备，1：删除分组
     */
    private void onDeleteDevice(int type) {
        if (!isGroupSelect()) {
            return;
        }

        AlertBean bean = new AlertBean();
        bean.setTitle(getString(R.string.txt_tip));
        if (type == 0) {
            bean.setAlert(getString(R.string.txt_device_delete_hint));
        } else {
            bean.setAlert(getString(R.string.txt_group_device_delete_hint));
        }
        bean.setType(0);
        AlertAppDialog dialog = new AlertAppDialog();
        dialog.show(getSupportFragmentManager(), bean, new AlertAppDialog.onAlertDialogChange() {
            @Override
            public void onConfirm() {
                if (type == 0) {
                    submitDeleteDevice();
                } else {
                    submitDeleteGroup();
                }
            }

            @Override
            public void onCancel() {

            }
        });
    }

    /**
     * 删除分组
     */
    private void submitDeleteGroup() {
        GroupDeletePutBean.ParamsBean paramsBean = new GroupDeletePutBean.ParamsBean();
        paramsBean.setGid(mSid);

        GroupDeletePutBean bean = new GroupDeletePutBean();
        bean.setParams(paramsBean);
        bean.setFunc(ModuleValueService.Fuc_For_Group_Delete);
        bean.setModule(ModuleValueService.Module_For_Group_Delete);

        if (getPresenter() != null) {
            getPresenter().submitDeleteGroup(bean);
        }
    }

    /**
     * 删除设备
     */
    private void submitDeleteDevice() {
        RemoveDevicePutBean.ParamsBean paramsBean = new RemoveDevicePutBean.ParamsBean();
        paramsBean.setSgid(mSid);
        paramsBean.setDel_type(ResultDataUtils.Device_Delete_Parent);

        RemoveDevicePutBean bean = new RemoveDevicePutBean();
        bean.setParams(paramsBean);
        bean.setFunc(ModuleValueService.Fuc_For_Device_Remove);
        bean.setModule(ModuleValueService.Module_For_Device_Remove);

        if (getPresenter() != null) {
            getPresenter().submitDeleteDevice(bean);
        }
    }

    /**
     * 编辑分组名称
     */
    private void editGroupName() {
        if (!isGroupSelect()) {
            return;
        }
        GroupEditDialog dialog = new GroupEditDialog();
        dialog.show(getSupportFragmentManager(), mSid, mGroupName, new GroupEditDialog.onGroupEditChange() {
            @Override
            public void onGroupEdit(String groupName, String gid) {
                submitGroupEdit(groupName, gid);
            }
        });
    }

    /**
     * 提交编辑分组
     *
     * @param groupName 分组名称
     * @param gid       分组id
     */
    private void submitGroupEdit(String groupName, String gid) {
        GroupEditPutBean.ParamsBean paramsBean = new GroupEditPutBean.ParamsBean();
        paramsBean.setGid(gid);
        paramsBean.setGroup_name(groupName);

        GroupEditPutBean bean = new GroupEditPutBean();
        bean.setParams(paramsBean);
        bean.setFunc(ModuleValueService.Fuc_For_Group_Edit);
        bean.setModule(ModuleValueService.Module_For_Group_Edit);

        if (getPresenter() != null) {
            getPresenter().submitGroupEdit(bean);
        }
    }

    /**
     * 添加新的分组
     */
    private void addNewGroup() {
        GroupAddDialog dialog = new GroupAddDialog();
        dialog.show(getSupportFragmentManager(), new GroupAddDialog.onGroupAddChange() {
            @Override
            public void onGroupAdd(String groupName) {
                submitGroupAdd(groupName);
            }
        });
    }

    /**
     * 添加分组
     */
    private void submitGroupAdd(String groupName) {
        GroupAddPutBean.ParamsBean paramsBean = new GroupAddPutBean.ParamsBean();
        paramsBean.setGroup_name(groupName);
        paramsBean.setFamilyid(ConstantValue.getFamilySid());

        GroupAddPutBean bean = new GroupAddPutBean();
        bean.setParams(paramsBean);
        bean.setFunc(ModuleValueService.Fuc_For_Group_Add);
        bean.setModule(ModuleValueService.Module_For_Group_Add);

        if (getPresenter() != null) {
            getPresenter().submitGroupAdd(bean);
        }
    }

    /**
     * 添加设备
     */
    private void addNewDevice() {
    }

    /**
     * 获取任务进度
     */
    private void getTaskProgress() {
        TaskProgressPubBean.ParamsBean paramsBean = new TaskProgressPubBean.ParamsBean();
        paramsBean.setTask_id(taskId);

        TaskProgressPubBean bean = new TaskProgressPubBean();
        bean.setParams(paramsBean);
        bean.setFunc(ModuleValueService.Fuc_For_Task_Progress);
        bean.setModule(ModuleValueService.Module_For_Task_Progress);

        if (getPresenter() != null) {
            getPresenter().getTaskProgress(bean);
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(@NonNull Message msg) {
            mCountDown--;
            if (mCountDown <= 0) {
                mCountDown = mCountDownTime;
                getTaskProgress();
            }
            handler.sendEmptyMessageDelayed(1, 1000);
        }
    };

    @Override
    public void getDeviceGroupListSuccess(DeviceGroupResultBean deviceGroupResultBean, boolean isRefresh) {
        if (isRefresh) {
            groupBeans.clear();
        }
        if (deviceGroupResultBean.getGarages() != null && deviceGroupResultBean.getGarages().size() > 0) {
            mLastGroupId = deviceGroupResultBean.getGarages().get(deviceGroupResultBean.getGarages().size() - 1).getSid();
            groupBeans.addAll(deviceGroupResultBean.getGarages());
        }
        mAdapter.notifyDataSetChanged();
        onShowNoData();
    }

    @Override
    public void finishRefresh() {
        srlView.setRefreshing(false);
    }

    @Override
    public void endLoadMore() {
        mAdapter.loadMoreComplete();
    }

    @Override
    public void setNoMore() {
        mAdapter.loadMoreEnd();
    }

    @Override
    public void endLoadFail() {
        mAdapter.loadMoreFail();
    }

    @Override
    public void submitGroupAddSuccess(GroupAddResultBean groupAddResultBean) {
        ToastUtils.showShort(getString(R.string.txt_add_group_success));
        getData();
    }

    @Override
    public void submitGroupEditSuccess(BaseBean baseBean, String name, String gid) {
        ToastUtils.showShort(getString(R.string.txt_edit_group_success));
        mGroupName = name;
        for (DeviceGroupResultBean.GaragesBean bean : groupBeans) {
            if (bean.getSid().equals(gid)) {
                bean.setSname(name);
                break;
            }
        }
        mAdapter.notifyDataSetChanged();
        setResult(Activity.RESULT_OK);
    }

    @Override
    public void submitTransferDeviceSuccess(DeviceBaseResultBean deviceBaseResultBean) {
        setResult(Activity.RESULT_OK);
        taskProgressDialog = null;
        taskId = "";
        if (!TextUtils.isEmpty(deviceBaseResultBean.getTask_id())) {
            taskId = deviceBaseResultBean.getTask_id();
            handler.sendEmptyMessage(1);
            if (taskProgressDialog == null) {
                taskProgressDialog = new TaskProgressDialog();
                taskProgressDialog.show(getSupportFragmentManager(), new TaskProgressDialog.onProgressBarChange() {
                    @Override
                    public void onTaskProgressFinish(String return_message, long return_code) {
                        handler.removeCallbacksAndMessages(null);

                        if (return_code == 0) {
                            ToastUtils.showShort(getString(R.string.txt_transfer_device_success));
                        } else {
                            ToastUtils.showShort(return_message);
                        }
                        tvRight.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                getData();
                            }
                        }, 500);
                    }
                });
            }
        } else {
            ToastUtils.showShort(getString(R.string.txt_transfer_device_success));
            getData();

            if (deviceBaseResultBean.getFail_items() != null && deviceBaseResultBean.getFail_items().size() > 0) {
                DeviceFailDialog dialog = new DeviceFailDialog();
                dialog.show(getSupportFragmentManager(), deviceBaseResultBean.getFail_items(), 1);
            }
        }
    }

    @Override
    public void submitDeleteDeviceSuccess(DeviceBaseResultBean deviceBaseResultBean) {
        setResult(Activity.RESULT_OK);
        taskProgressDialog = null;
        taskId = "";
        if (!TextUtils.isEmpty(deviceBaseResultBean.getTask_id())) {
            taskId = deviceBaseResultBean.getTask_id();
            handler.sendEmptyMessage(1);
            if (taskProgressDialog == null) {
                taskProgressDialog = new TaskProgressDialog();
                taskProgressDialog.show(getSupportFragmentManager(), new TaskProgressDialog.onProgressBarChange() {
                    @Override
                    public void onTaskProgressFinish(String return_message, long return_code) {
                        handler.removeCallbacksAndMessages(null);

                        if (return_code == 0) {
                            ToastUtils.showShort(getString(R.string.txt_successful_operation));
                        } else {
                            ToastUtils.showShort(return_message);
                        }
                        tvRight.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                getData();
                            }
                        }, 500);
                    }
                });
            }
        } else {
            ToastUtils.showShort(getString(R.string.txt_successful_operation));
            getData();

            if (deviceBaseResultBean.getFail_items() != null && deviceBaseResultBean.getFail_items().size() > 0) {
                DeviceFailDialog dialog = new DeviceFailDialog();
                dialog.show(getSupportFragmentManager(), deviceBaseResultBean.getFail_items(), 3);
            }
        }
    }

    @Override
    public void submitDeleteGroupSuccess(BaseBean baseBean) {
        setResult(Activity.RESULT_OK);
        ToastUtils.showShort(getString(R.string.txt_delete_success));
        getData();
    }

    @Override
    public void submitFreezeEquipmentSuccess(DeviceBaseResultBean deviceBaseResultBean) {
        setResult(Activity.RESULT_OK);
        taskProgressDialog = null;
        taskId = "";
        if (!TextUtils.isEmpty(deviceBaseResultBean.getTask_id())) {
            taskId = deviceBaseResultBean.getTask_id();
            handler.sendEmptyMessage(1);
            if (taskProgressDialog == null) {
                taskProgressDialog = new TaskProgressDialog();
                taskProgressDialog.show(getSupportFragmentManager(), new TaskProgressDialog.onProgressBarChange() {
                    @Override
                    public void onTaskProgressFinish(String return_message, long return_code) {
                        handler.removeCallbacksAndMessages(null);

                        if (return_code == 0) {
                            ToastUtils.showShort(getString(R.string.txt_successful_operation));
                        } else {
                            ToastUtils.showShort(return_message);
                        }
                        tvRight.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                getData();
                            }
                        }, 500);
                    }
                });
            }
        } else {
            ToastUtils.showShort(getString(R.string.txt_successful_operation));
            getData();
            if (deviceBaseResultBean.getFail_items() != null && deviceBaseResultBean.getFail_items().size() > 0) {
                DeviceFailDialog dialog = new DeviceFailDialog();
                dialog.show(getSupportFragmentManager(), deviceBaseResultBean.getFail_items(), 4);
            }
        }
    }

    @Override
    public void getTaskProgressSuccess(TaskProgressResultBean taskProgressResultBean) {
        if (taskProgressDialog != null) {
            double progress = ((double) taskProgressResultBean.getCurrent_count() / taskProgressResultBean.getTotal()) * 100;
            int intProgress = (int) progress;
            if (intProgress > 100) {
                intProgress = 100;
            }
            taskProgressDialog.setProgressBar(intProgress, taskProgressResultBean.isIs_finish(),
                    taskProgressResultBean.getReturn_msg(), taskProgressResultBean.getReturn_code());
        }
    }

    @Override
    public void onRefreshData() {
        mSid = "";
        mLastGroupId = "";
        mGroupName = "";
        getDeviceGroupList(false, true);
    }

    @Override
    public void onBackPressed() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }
}
