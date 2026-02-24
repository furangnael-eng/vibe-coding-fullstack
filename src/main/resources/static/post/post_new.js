document.getElementById('post-form').addEventListener('submit', async (e) => {
    e.preventDefault();
    clearErrors();

    const title = document.getElementById('title').value.trim();
    const content = document.getElementById('content').value.trim();
    const tags = document.getElementById('tags').value.trim();

    let valid = true;
    if (!title) { showError('title-error', '제목은 필수 입력 항목입니다.'); valid = false; }
    if (!content) { showError('content-error', '내용은 필수 입력 항목입니다.'); valid = false; }
    if (!valid) return;

    const btn = document.getElementById('submit-btn');
    btn.disabled = true;
    btn.textContent = '등록 중...';

    try {
        const res = await fetch('/api/posts', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ title, content, tags: tags || null })
        });

        if (res.status === 201) {
            alert('게시글이 등록되었습니다.');
            location.href = '/post/posts.html';
            return;
        }

        const data = await res.json();
        if (res.status === 400) {
            // Bean Validation 또는 IllegalArgumentException 오류
            const msg = data.error ?? '입력값을 확인해주세요.';
            if (msg.includes('태그')) showError('tags-error', msg);
            else if (msg.includes('제목')) showError('title-error', msg);
            else if (msg.includes('내용')) showError('content-error', msg);
            else showError('tags-error', msg);
        } else {
            alert(data.error ?? '서버 오류가 발생했습니다.');
        }
    } catch (e) {
        alert('네트워크 오류가 발생했습니다.');
    } finally {
        btn.disabled = false;
        btn.textContent = '등록';
    }
});

function showError(id, msg) {
    document.getElementById(id).textContent = msg;
}

function clearErrors() {
    document.querySelectorAll('.error-msg').forEach(el => { el.textContent = ''; });
}
