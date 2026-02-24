const params = new URLSearchParams(window.location.search);
const no = params.get('no');

if (!no) {
    alert('잘못된 접근입니다.');
    location.href = '/post/posts.html';
}

document.getElementById('edit-btn').href = `/post/post_edit.html?no=${no}`;
document.getElementById('back-btn').href = '/post/posts.html';

async function loadPost() {
    try {
        const res = await fetch(`/api/posts/${no}`);
        if (res.status === 404) {
            alert('게시글을 찾을 수 없습니다.');
            location.href = '/post/posts.html';
            return;
        }
        if (!res.ok) throw new Error('게시글을 불러오지 못했습니다.');
        const post = await res.json();
        renderPost(post);
    } catch (e) {
        alert(e.message);
    }
}

function renderPost(post) {
    document.title = `${post.title} - 게시판`;
    document.getElementById('skeleton').style.display = 'none';
    document.getElementById('post-content').style.display = 'block';
    document.getElementById('title').textContent = post.title;
    document.getElementById('content').textContent = post.content;
    document.getElementById('created-at').textContent = formatDateTime(post.createdAt);
    document.getElementById('updated-at').textContent = post.updatedAt ? formatDateTime(post.updatedAt) : '-';
    document.getElementById('views').textContent = post.views ?? 0;

    const tagsEl = document.getElementById('tags');
    if (post.tags && post.tags.length > 0) {
        tagsEl.innerHTML = post.tags.map(t => `<span class="tag">#${escapeHtml(t)}</span>`).join('');
    } else {
        tagsEl.style.display = 'none';
    }
}

document.getElementById('delete-btn').addEventListener('click', async () => {
    if (!confirm('정말로 삭제하시겠습니까?')) return;
    try {
        const res = await fetch(`/api/posts/${no}`, { method: 'DELETE' });
        if (!res.ok) throw new Error('삭제에 실패했습니다.');
        alert('삭제되었습니다.');
        location.href = '/post/posts.html';
    } catch (e) {
        alert(e.message);
    }
});

function formatDateTime(iso) {
    if (!iso) return '';
    return new Date(iso).toLocaleString('ko-KR', { year: 'numeric', month: '2-digit', day: '2-digit', hour: '2-digit', minute: '2-digit' });
}

function escapeHtml(str) {
    return (str ?? '').replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;');
}

loadPost();
