package org.algohub.engine.judge;

import org.algohub.engine.type.LanguageType;
import org.junit.Test;

@SuppressWarnings({"PMD.CommentRequired"})
public class JavaJudgeTest {

  @Test public void judgeTest() {
    JudgeEngineTestUtil.batchJudge(LanguageType.JAVA);
  }
}
