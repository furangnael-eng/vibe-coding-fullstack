const PAGE_SIZE = 5;
let currentPage = 1;

async function loadPosts(page) {
  currentPage = page;
  try {
    const res = await fetch(`/api/posts?page=${page}&size=${PAGE_SIZE}`);
    if (!res.ok) throw new Error('데이터를 불러오지 못했습니다.');
    const data = await res.json();
    renderPosts(data.posts);
    renderPagination(data.currentPage, data.totalPages);
  } catch (e) {
    showError(e.message);
  }
}

function renderPosts(posts) {
  const container = document.getElementById('post-list');
  if (!posts || posts.length === 0) {
    container.innerHTML = `
      <div class="empty-state">
        <div class="icon">📭</div>
        <p>아직 게시글이 없습니다.</p>
      </div>`;
    return;
  }
  container.innerHTML = posts.map(p => `
    <a href="/post/post_detail.html?no=${p.no}" class="post-item">
      <span class="post-no">#${p.no}</span>
      <span class="post-title">${escapeHtml(p.title)}</span>
      <div class="post-meta">
        <span class="meta-badge">📅 ${formatDate(p.createdAt)}</span>
        <span class="meta-badge">👁 ${p.views ?? 0}</span>
      </div>
    </a>`).join('');
}

function renderPagination(current, total) {
  const container = document.getElementById('pagination');
  if (total <= 1) { container.innerHTML = ''; return; }
  let html = `<button class="page-btn" onclick="loadPosts(${current - 1})" ${current <= 1 ? 'disabled' : ''}>‹</button>`;
  for (let i = 1; i <= total; i++) {
    html += `<button class="page-btn ${i === current ? 'active' : ''}" onclick="loadPosts(${i})">${i}</button>`;
  }
  html += `<button class="page-btn" onclick="loadPosts(${current + 1})" ${current >= total ? 'disabled' : ''}>›</button>`;
  container.innerHTML = html;
}

function formatDate(iso) {
  if (!iso) return '';
  return new Date(iso).toLocaleDateString('ko-KR', { year: 'numeric', month: '2-digit', day: '2-digit' });
}

function escapeHtml(str) {
  return (str ?? '').replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;');
}

function showError(msg) {
  const toast = document.getElementById('error-toast');
  toast.textContent = msg;
  toast.style.display = 'block';
  setTimeout(() => { toast.style.display = 'none'; }, 3000);
}

loadPosts(1);
