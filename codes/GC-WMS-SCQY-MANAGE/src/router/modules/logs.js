/** When your routing table is too long, you can split it into small modules **/

import Layout from '@/layout'

const logsRouter = {
  path: '/logs',
  component: Layout,
  redirect: 'noRedirect',
  name: 'Logs',
  meta: {
    title: '日志管理',
    icon: 'logs'
  },
  children: [
    {
      path: 'admin/login',
      component: () => import('@/views/admin/logs/login/index'),
      name: 'login',
      meta: { title: '登录日志' }
    },
    {
      path: 'admin/operator',
      component: () => import('@/views/admin/logs/operator/index'),
      name: 'operator',
      meta: { title: '操作日志' }
    }
  ]
}
export default logsRouter
