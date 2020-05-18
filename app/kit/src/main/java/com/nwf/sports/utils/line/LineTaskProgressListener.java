package com.nwf.sports.utils.line;

/**
 * 线路任务UI回调器
 */
public interface LineTaskProgressListener {

    /**
     * 向外回调进度
     * 现在暂时没有使用
     *
     * @param current
     */
    void onProgressBarChange(int current, int max);

    /**
     * 向外简单错误原因
     *
     * @param result
     */
    void onErrorSimpleReason(String result);

    /**
     * 向外回调错误 bean
     *
     * @param lineErrorDialogBean
     */
    void onErrorComplexReason(LineErrorDialogBean lineErrorDialogBean);

    /**
     * 线路成功 回调
     *
     * @param domain
     */
    void onGetLineSuccess(String domain);
}
