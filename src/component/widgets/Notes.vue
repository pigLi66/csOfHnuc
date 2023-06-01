eslint-disable eqeqeq
<template>
  <section>
    <el-main class="noPadding" style="height: 300px">
      <el-row justify="center" type="flex">
        <div class="widget-label">便签</div>
      </el-row>
      <el-tabs
        v-model="activeNote"
        style="max-height: 250px"
        tab-position="left"
      >
        <el-tab-pane
          v-for="singleNotesData in notesData"
          :key="singleNotesData"
          :label="singleNotesData.name"
          :name="singleNotesData.token"
          class="scrollbar-div"
          style="padding-left: 0px"
        >
          <template #label>
            <span>
              <el-dropdown
                v-show="activeNote === singleNotesData.token"
                placement="bottom"
                show-timeout="50"
                size="small"
                @command="actionClicked"
              >
                <span class="el-dropdown-link">
                  <i class="el-icon-more" style="margin-right: 4px" />
                </span>
                <template #dropdown>
                  <el-dropdown-menu>
                    <el-dropdown-item
                      command="edit"
                      icon="el-icon-edit"
                      style="color: #409eff"
                      >编辑
                    </el-dropdown-item>
                    <el-dropdown-item
                      command="bell"
                      icon="el-icon-bell"
                      style="color: #e6a23c"
                      >提醒
                    </el-dropdown-item>
                    <el-dropdown-item
                      command="delete"
                      icon="el-icon-delete"
                      style="color: #f56c6c"
                      >删除
                    </el-dropdown-item>
                  </el-dropdown-menu>
                </template>
              </el-dropdown>
              {{ singleNotesData.name }}
            </span>
          </template>

          <p
            class="notesText scrollbar-div"
            style="
              padding-right: 10px;
              max-width: 93%;
              max-height: 250px;
              min-height: 250px;
            "
          >
            {{ singleNotesData.content }}
          </p>
        </el-tab-pane>
      </el-tabs>
    </el-main>
    <el-footer
      v-if="userId != 0"
      height="31px"
      style="justify-content: center; display: flex"
    >
      <WidgetButton
        :buttons="buttons"
        :user_id="userId"
        :widget_id="widgetId"
        @add="add()"
        @revert="revertClicked()"
      >
      </WidgetButton>
    </el-footer>

    <!-- 编辑dialog -->
    <el-dialog
      v-model:visible="edit.visible"
      :close-on-click-modal="false"
      :title="edit.dialogTitle"
    >
      <div class="div-flex">
        <div class="notesEditFormLabel">标题：</div>
        <el-input
          v-model="edit.title"
          class="margin_bottom-medium"
          placeholder="请输入便签标题"
          size="small"
          style="width: 80%"
        ></el-input>
      </div>
      <div class="div-flex">
        <div class="notesEditFormLabel">内容：</div>
        <el-input
          v-model="edit.content"
          autosize
          class="margin_bottom-large"
          placeholder="请输入便签内容"
          size="small"
          style="width: 80%"
          type="textarea"
        ></el-input>
      </div>
      <div class="dialog-footer">
        <el-button size="small" @click="edit.visible = false">取消</el-button>
        <el-button size="small" type="primary" @click="editSubmit()"
          >确定</el-button
        >
      </div>
    </el-dialog>

    <!-- 推送dialog -->
    <el-dialog v-model:visible="notify.visible" title="提醒">
      <el-form
        ref="form"
        :model="notify.form"
        class="padding_bottom-medium"
        size="mini"
      >
        <el-form-item label="标题">
          <p
            class="notesText"
            style="
              font-size: 14px;
              padding-top: 0px;
              margin-top: 0px;
              line-height: 28px;
              margin-bottom: 0px;
            "
          >
            {{ notify.form.title }}
          </p>
        </el-form-item>
        <el-form-item label="内容">
          <p
            class="notesText"
            style="
              font-size: 14px;
              margin-left: 40px;
              line-height: 28px;
              padding-top: 0px;
              margin-top: 0px;
              margin-bottom: 0px;
            "
          >
            {{ notify.form.content }}
          </p>
        </el-form-item>
        <el-form-item label="提醒方式">
          <div class="div-flex" style="width: 324px">
            <el-select
              v-model="notify.form.notifyMethod.select"
              class="main_select--medium"
              placeholder="请选择"
              size="small"
            >
              <el-option
                v-for="item in notify.form.notifyMethod.options"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              ></el-option>
            </el-select>
          </div>
        </el-form-item>
        <el-form-item label="提醒时间">
          <div class="div-flex">
            <el-date-picker
              v-model="notify.form.triggerDate"
              class="main_select--medium"
              placeholder="选择日期"
              size="small"
              type="date"
              value-format="yyyy-MM-dd"
            ></el-date-picker>
            <el-time-select
              v-model="notify.form.triggerTime"
              :picker-options="{
                start: '00:00',
                step: '01:00',
                end: '24:00',
              }"
              class="main_select--medium"
              placeholder="选择时间"
              size="small"
            ></el-time-select>
          </div>
        </el-form-item>
        <p
          class="notesText"
          style="
            font-size: 12px;
            color: #f56c6c;
            padding-top: 0px;
            margin-top: 0px;
            margin-bottom: 0px;
          "
        >
          *提交后不能取消，但可以多次提交。
        </p>
      </el-form>
      <span class="dialog-footer">
        <el-button size="small" type="primary" @click="notify.visible = false">
          取消
        </el-button>
        <el-button size="small" type="primary" @click="notifySubmit()">
          确定
        </el-button>
      </span>
    </el-dialog>

    <!-- 时间机器dialog -->
    <el-dialog v-model:visible="revert.visible" title="时间机器">
      <p
        class="notesText"
        style="
          font-size: 12px;
          color: #f56c6c;
          padding-top: 0px;
          margin-top: 0px;
          margin-bottom: 0px;
        "
      >
        *可以恢复到最近的五个版本中的任意一个，此操作不会对过去的版本产生影响，而是会使用以前版本的内容生成一个新版本。
      </p>
      <el-table
        :data="revert.list"
        size="small"
        style="text-align: center; margin-bottom: 20px"
      >
        <el-table-column label="版本" prop="update_time"></el-table-column>
        <el-table-column
          label="创建人"
          prop="user"
          width="100"
        ></el-table-column>
        <el-table-column :key="Math.random()" label="操作" width="150">
          <template #default="scope">
            <el-popover placement="right" trigger="hover" width="350">
              <el-table
                :data="scope.row.detail"
                size="small"
                style="text-align: center"
              >
                <el-table-column
                  label="标题"
                  prop="name"
                  width="100"
                ></el-table-column>
                <el-table-column label="内容">
                  <template #default="innerScope">
                    <span v-if="innerScope.row.content.length < 25">
                      {{ innerScope.row.content }}
                    </span>
                    <el-popover
                      :content="innerScope.row.content"
                      placement="top"
                      trigger="hover"
                      width="200"
                    >
                      <span v-if="innerScope.row.content.length >= 25">{{
                        innerScope.row.content.substring(0, 25) + "..."
                      }}</span>
                    </el-popover>
                  </template>
                </el-table-column>
              </el-table>
              <template #reference>
                <el-button
                  class="noMargin"
                  plain
                  size="mini"
                  type="primary"
                  @click="
                    revertConfirm(scope.row.update_time, scope.row.detail)
                  "
                  >恢复至此版本
                </el-button>
              </template>
            </el-popover>
          </template>
        </el-table-column>
      </el-table>
      <div class="dialog-footer">
        <el-button size="small" @click="revert.visible = false">取消</el-button>
      </div>
    </el-dialog>
  </section>
</template>
<script lang="ts">
import WidgetButton from "../common/WidgetButton.vue";
import * as noteApi from "@/api/notesApi";
import { defineComponent } from "vue";
import { Notes } from "@/type/Notes";
import { ElMessage, ElMessageBox } from "element-plus";

// eslint-disable-next-line @typescript-eslint/no-unused-vars
const api = {
  get: "/notes/get",
  save: "/notes/save",
  notify: "/notes/notify",
  revert: "/notes/revert",
};

export default defineComponent({
  name: "notes",
  props: {
    userId: Number,
    widgetId: Number,
    buttons: Array,
    flush: Number,
  },
  components: {
    WidgetButton,
  },
  data() {
    return {
      notesData: [] as Notes[],
      activeNote: "",
      edit: {
        noteIndex: -1,
        visible: false,
        dialogTitle: "",
        title: "",
        content: "",
      },
      timer: 0,
      notify: {
        visible: false,
        form: {
          title: "",
          content: "",
          notifyMethod: {
            select: 1,
            options: [
              {
                value: 1,
                label: "微信",
              },
              {
                value: 2,
                label: "邮件",
              },
            ],
          },
          triggerDate: "",
          triggerTime: "",
        },
      },
      revert: {
        visible: false,
        list: [],
      },
    };
  },
  methods: {
    actionClicked(command: string) {
      if (command === "edit") {
        this.editClicked(this.activeNote);
      }
      if (command === "bell") {
        this.notifyClicked(this.activeNote);
      }
      if (command === "delete") {
        this.del(this.activeNote);
      }
    },
    notesGetIndex(notesName: string): number {
      for (let x = 0; x < this.notesData.length; x++) {
        if (this.notesData[x].token === notesName) {
          return x;
        }
      }
      return -1;
    },
    async notesGet() {
      const resp = await noteApi.get({
        // eslint-disable-next-line @typescript-eslint/no-non-null-assertion
        userId: this.userId!,
      });
      this.notesData = resp.data.list;
      if (this.notesData.length !== 0) {
        this.activeNote = this.notesData[0].token;
        for (let x = 0; x < this.notesData.length; x++) {
          this.notesData[x].content
            .replace(/\n/g, "<br>")
            .replace(/\s/g, "&nbsp;");
        }
      }
      this.$emit("done");
    },
    async notesSave() {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const resp = await noteApi.save({
        userId: this.userId,
        notes: this.notesData,
      });
      ElMessage.success("保存成功");
    },
    add() {
      this.edit.dialogTitle = "新建";
      const d = new Date();
      this.edit.title = d.getMonth() + 1 + "." + d.getDate();
      this.edit.content = "";
      this.edit.visible = true;
    },
    async del(notesName: string) {
      ElMessageBox.confirm("确认删除吗?", "提示", {}).then(async() => {
        // eslint-disable-next-line @typescript-eslint/no-non-null-assertion
        const i = this.notesGetIndex(notesName)!;
        this.edit.noteIndex = i;
        this.notesData.splice(i, 1);
        await this.notesSave();
        await this.notesGet();
      });
    },
    editClicked(notesName: string) {
      const i = this.notesGetIndex(notesName);
      this.edit.noteIndex = i;
      this.edit.dialogTitle = "编辑";
      this.edit.title = this.notesData[i].name;
      this.edit.content = this.notesData[i].content;
      this.edit.visible = true;
    },
    async editSubmit() {
      if (this.edit.dialogTitle === "编辑") {
        this.notesData[this.edit.noteIndex].title = this.edit.title;
        this.notesData[this.edit.noteIndex].content = this.edit.content;
      } else if (this.edit.dialogTitle === "新建") {
        const timestamp = new Date().getTime();
        const salt = Math.floor(Math.random() * 10000000000000000);
        this.notesData.push({
          name: this.edit.title,
          content: this.edit.content,
          token: String(timestamp) + String(salt),
        });
      }
      await this.notesSave();
      await this.notesGet();
      this.edit.visible = false;
    },
    notifyClicked(notesName: string) {
      const i = this.notesGetIndex(notesName);
      this.notify.form.title = this.notesData[i].name;
      this.notify.form.content = this.notesData[i].content;
      this.notify.visible = true;
    },
    async notifySubmit() {
      await noteApi.notify({
        // eslint-disable-next-line @typescript-eslint/no-non-null-assertion
        userId: this.userId!,
        title: this.notify.form.title,
        content: this.notify.form.content,
        method: this.notify.form.notifyMethod.select,
        notifyTriggerTime:
          this.notify.form.triggerDate + " " + this.notify.form.triggerTime,
      });
      ElMessage.success("成功");
      this.notify.visible = false;
    },
    async revertClicked() {
      const resp = await noteApi.revert({
        // eslint-disable-next-line @typescript-eslint/no-non-null-assertion
        userId: this.userId!,
      });
      this.revert.list = resp.data.list;
      this.revert.visible = true;
    },
    async revertConfirm(updateTime: string, detail: Array<Notes>) {
      ElMessageBox.confirm(
        "确认使用时间机器恢复至[" + updateTime + "]的版本吗?",
        "提示",
        {}
      ).then(async() => {
        this.notesData = detail;
        await this.notesSave();
        await this.notesGet();
        this.revert.visible = false;
      });
    },
  },
  mounted() {
    this.notesGet();
    this.timer = window.setInterval(this.notesGet, this.flush);
  },
  beforeUnmount() {
    window.clearInterval(this.timer);
  },
});
</script>
<style scoped>
.notesText {
  color: #606266;
  font-size: 15px;
  text-align: left;
  line-height: 35px;
  margin-left: 10px;
  margin-right: 20px;
  margin-top: 0px;
  font-family: Helvetica Neue, Helvetica, PingFang SC, Hiragino Sans GB,
    Microsoft YaHei, SimSun, sans-serif;
}

.notesEditFormLabel {
  color: #606266;
  font-size: 15px;
  text-align: left;
  padding-top: 5px;
  font-family: Helvetica Neue, Helvetica, PingFang SC, Hiragino Sans GB,
    Microsoft YaHei, SimSun, sans-serif;
}
</style>
