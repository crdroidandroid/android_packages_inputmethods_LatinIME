/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.inputmethod.latin;

import com.android.inputmethod.latin.NgramContext.WordInfo;

import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.SmallTest;

@SmallTest
public class NgramContextTests extends AndroidTestCase {
    public void testConstruct() {
        assertEquals(new NgramContext(new WordInfo("a")), new NgramContext(new WordInfo("a")));
        assertEquals(new NgramContext(WordInfo.BEGINNING_OF_SENTENCE),
                new NgramContext(WordInfo.BEGINNING_OF_SENTENCE));
        assertEquals(new NgramContext(WordInfo.EMPTY_WORD_INFO),
                new NgramContext(WordInfo.EMPTY_WORD_INFO));
        assertEquals(new NgramContext(WordInfo.EMPTY_WORD_INFO),
                new NgramContext(WordInfo.EMPTY_WORD_INFO));
    }

    public void testIsBeginningOfSentenceContext() {
        assertFalse(new NgramContext().isBeginningOfSentenceContext());
        assertTrue(new NgramContext(WordInfo.BEGINNING_OF_SENTENCE)
                .isBeginningOfSentenceContext());
        assertTrue(NgramContext.BEGINNING_OF_SENTENCE.isBeginningOfSentenceContext());
        assertFalse(new NgramContext(new WordInfo("a")).isBeginningOfSentenceContext());
        assertFalse(new NgramContext(new WordInfo("")).isBeginningOfSentenceContext());
        assertFalse(new NgramContext(WordInfo.EMPTY_WORD_INFO).isBeginningOfSentenceContext());
        assertTrue(new NgramContext(WordInfo.BEGINNING_OF_SENTENCE, new WordInfo("a"))
                .isBeginningOfSentenceContext());
        assertFalse(new NgramContext(new WordInfo("a"), WordInfo.BEGINNING_OF_SENTENCE)
                .isBeginningOfSentenceContext());
        assertFalse(new NgramContext(WordInfo.EMPTY_WORD_INFO, WordInfo.BEGINNING_OF_SENTENCE)
                .isBeginningOfSentenceContext());
    }

    public void testGetNextNgramContext() {
        final NgramContext ngramContext_a = new NgramContext(new WordInfo("a"));
        final NgramContext ngramContext_b_a =
                ngramContext_a.getNextNgramContext(new WordInfo("b"));
        assertEquals("b", ngramContext_b_a.getNthPrevWord(1));
        assertEquals("a", ngramContext_b_a.getNthPrevWord(2));
        final NgramContext ngramContext_bos_b =
                ngramContext_b_a.getNextNgramContext(WordInfo.BEGINNING_OF_SENTENCE);
        assertTrue(ngramContext_bos_b.isBeginningOfSentenceContext());
        assertEquals("b", ngramContext_bos_b.getNthPrevWord(2));
        final NgramContext ngramContext_c_bos =
                ngramContext_b_a.getNextNgramContext(new WordInfo("c"));
        assertEquals("c", ngramContext_c_bos.getNthPrevWord(1));
    }
}
